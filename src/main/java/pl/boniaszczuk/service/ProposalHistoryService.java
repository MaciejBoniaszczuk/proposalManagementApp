package pl.boniaszczuk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.boniaszczuk.entity.Proposal;
import pl.boniaszczuk.entity.ProposalHistory;
import pl.boniaszczuk.entity.ProposalStatus;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;
import pl.boniaszczuk.repository.ProposalHistoryRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ProposalHistoryService {

    private final ProposalHistoryRepository proposalHistoryRepository;
    private final ProposalStatusService proposalStatusService;


    public void createInitialHistoryForProposal(Proposal proposalEntity) {
        saveHistory(proposalEntity, null, ProposalStatusEnum.CREATED);
    }

    public void saveDeletionInHistory(Proposal proposal) {
        saveHistory(proposal, proposal.getProposalStatus(), ProposalStatusEnum.DELETED);
    }

    public void saveRejectionInHistory(Proposal proposal) {
        saveHistory(proposal, proposal.getProposalStatus(), ProposalStatusEnum.REJECTED);
    }

    public void saveVerifiedInHistory(Proposal proposal) {
        saveHistory(proposal, proposal.getProposalStatus(), ProposalStatusEnum.VERIFIED);
    }

    public void saveAcceptedInHistory(Proposal proposal) {
        saveHistory(proposal, proposal.getProposalStatus(), ProposalStatusEnum.ACCEPTED);
    }

    public void savePublishedInHistory(Proposal proposal) {
        saveHistory(proposal, proposal.getProposalStatus(), ProposalStatusEnum.PUBLISHED);
    }

    private void saveHistory(Proposal proposal, ProposalStatus fromStatus, ProposalStatusEnum toStatusEnum) {
        ProposalHistory proposalHistory = new ProposalHistory();
        proposalHistory.setProposal(proposal);
        proposalHistory.setFromStatus(fromStatus);
        proposalHistory.setToStatus(proposalStatusService.getProposalStatusByIdentifier(toStatusEnum));
        proposalHistory.setChangeDate(LocalDateTime.now());
        proposalHistoryRepository.save(proposalHistory);
    }
}
