package pl.boniaszczuk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.boniaszczuk.entity.Proposal;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;
import pl.boniaszczuk.exception.NoProposalsInDatabaseException;
import pl.boniaszczuk.exception.ProposalAlreadyExistsException;
import pl.boniaszczuk.exception.ProposalNotFoundException;
import pl.boniaszczuk.exception.ReasonNotProvidedException;
import pl.boniaszczuk.exception.UnableToAcceptProposal;
import pl.boniaszczuk.exception.UnableToDeleteProposalException;
import pl.boniaszczuk.exception.UnableToPublishProposal;
import pl.boniaszczuk.exception.UnableToRejectProposalException;
import pl.boniaszczuk.exception.UnableToUpdateProposalException;
import pl.boniaszczuk.exception.UnableToVerifyProposal;
import pl.boniaszczuk.mapper.ProposalMapper;
import pl.boniaszczuk.model.ActionReason;
import pl.boniaszczuk.model.ProposalModel;
import pl.boniaszczuk.model.ProposalParams;
import pl.boniaszczuk.repository.ProposalRepository;
import pl.boniaszczuk.utils.ProposalSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProposalService {

    private final ProposalStatusService proposalStatusService;
    private final ProposalRepository proposalRepository;
    private final ProposalHistoryService proposalHistoryService;
    private final ProposalMapper proposalMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void createProposal(ProposalModel proposalModel) {
        checkIfProposalExists(proposalModel);
        Proposal proposalEntity = proposalMapper.mapToEntity(proposalModel);
        proposalEntity.setCreateDate(getCreateDate());
        proposalEntity.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.CREATED));
        proposalHistoryService.createInitialHistoryForProposal(proposalEntity);
        proposalRepository.save(proposalEntity);
    }

    private LocalDateTime getCreateDate() {
        String formattedDateTime = LocalDateTime.now().format(formatter);
        return LocalDateTime.parse(formattedDateTime, formatter);
    }

    public Page<ProposalModel> getAll(ProposalParams params, Pageable pageable) {
        Specification<Proposal> specification = ProposalSpecification.withDynamicQuery(params);
        Page<Proposal> proposals = proposalRepository.findAll(specification, pageable);
        if(proposals.isEmpty()){
            throw new NoProposalsInDatabaseException("There are no proposals in database");
        }
        return proposals.map(proposalMapper::mapToModel);
    }

    public void deleteProposal(ActionReason deleteReason, Long id) {
        checkReason(deleteReason);
        Optional<Proposal> proposalOpt = proposalRepository.findById(id);
        proposalOpt.ifPresentOrElse(proposal -> {
                    if (proposal.getProposalStatus().getIdentifier() == ProposalStatusEnum.CREATED) {
                        proposal.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.DELETED));
                        proposal.setDeleteReason(deleteReason.reason());
                        proposalHistoryService.saveDeletionInHistory(proposal);
                    } else {
                        throw new UnableToDeleteProposalException("Only CREATED proposal can be deleted");
                    }
                },
                () -> {
                    throw new ProposalNotFoundException();
                }
        );
    }

    public void rejectProposal(ActionReason rejectReason, Long id) {
        checkReason(rejectReason);
        Optional<Proposal> proposalOpt = proposalRepository.findById(id);
        proposalOpt.ifPresentOrElse(proposal -> {
                    if (proposal.getProposalStatus().getIdentifier() == ProposalStatusEnum.VERIFIED ||
                            proposal.getProposalStatus().getIdentifier() == ProposalStatusEnum.ACCEPTED) {
                        proposal.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.REJECTED));
                        proposal.setRejectionReason(rejectReason.reason());
                        proposalHistoryService.saveRejectionInHistory(proposal);
                    } else {
                        throw new UnableToRejectProposalException("Only VERIFIED or ACCEPTED proposal can be removed");
                    }
                },
                () -> {
                    throw new ProposalNotFoundException();
                }
        );
    }

    public void verifyProposal(Long id) {
        Optional<Proposal> proposalOpt = proposalRepository.findById(id);
        proposalOpt.ifPresentOrElse(proposal -> {
                    if (proposal.getProposalStatus() == proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.CREATED)) {
                        proposal.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.VERIFIED));
                        proposalHistoryService.saveVerifiedInHistory(proposal);
                    } else throw new UnableToVerifyProposal("Only CREATED proposal can be verified");

                },
                () -> {
                    throw new ProposalNotFoundException();
                }
        );
    }

    public void acceptProposal(Long id) {
        Optional<Proposal> proposalOpt = proposalRepository.findById(id);
        proposalOpt.ifPresentOrElse(proposal -> {
                    if (proposal.getProposalStatus() == proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.VERIFIED)) {
                        proposal.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.ACCEPTED));
                        proposalHistoryService.saveAcceptedInHistory(proposal);
                    } else {
                        throw new UnableToAcceptProposal("Only VERIFIED proposal can be accepted");
                    }
                },
                () -> {
                    throw new ProposalNotFoundException();
                }
        );
    }

    public void publishProposal(Long id) {
        Optional<Proposal> proposalOpt = proposalRepository.findById(id);
        proposalOpt.ifPresentOrElse(proposal -> {
                    if (proposal.getProposalStatus() == proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.ACCEPTED)) {
                        proposal.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.PUBLISHED));
                        proposal.setUniqueNumber(getUniqueNumber(proposal.getId()));
                        proposalHistoryService.savePublishedInHistory(proposal);
                    } else {
                        throw new UnableToPublishProposal("Only ACCEPTED proposal can be published");
                    }
                },
                () -> {
                    throw new ProposalNotFoundException();
                }
        );
    }


    public void updateProposal(ProposalModel proposalModel) {
        Optional<Proposal> proposalOpt = proposalRepository.findByName(proposalModel.getName());
        proposalOpt.ifPresentOrElse(proposal -> {
                    if (proposal.getProposalStatus().getIdentifier() == ProposalStatusEnum.CREATED ||
                            proposal.getProposalStatus().getIdentifier() == ProposalStatusEnum.VERIFIED) {
                        proposal.setContent(proposalModel.getContent());
                    } else {
                        throw new UnableToUpdateProposalException("Proposal can be updated only for CREATED and VERIFIED proposals.");
                    }
                },
                () -> {
                    throw new ProposalNotFoundException();
                }
        );
    }

    private void checkReason(ActionReason deleteReason) {
        if (deleteReason.reason() == null || deleteReason.reason().isEmpty()) {
            throw new ReasonNotProvidedException("You have to provide reason");
        }
    }

    private void checkIfProposalExists(ProposalModel proposalModel) {
        Optional<Proposal> proposalOpt = proposalRepository.findByName(proposalModel.getName());
        if (proposalOpt.isPresent()) {
            throw new ProposalAlreadyExistsException(proposalModel.getName());
        }
    }

    private String getUniqueNumber(Long id) {
        int currentYear = LocalDate.now().getYear();
        return String.format("%d/%06d", currentYear, id);
    }
}
