package pl.boniaszczuk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.boniaszczuk.entity.Proposal;
import pl.boniaszczuk.entity.ProposalStatus;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;
import pl.boniaszczuk.exception.ReasonNotProvidedException;
import pl.boniaszczuk.exception.UnableToAcceptProposalException;
import pl.boniaszczuk.exception.UnableToDeleteProposalException;
import pl.boniaszczuk.exception.UnableToPublishProposalException;
import pl.boniaszczuk.exception.UnableToRejectProposalException;
import pl.boniaszczuk.exception.UnableToUpdateProposalException;
import pl.boniaszczuk.exception.UnableToVerifyProposalException;
import pl.boniaszczuk.model.ActionReason;
import pl.boniaszczuk.model.ProposalModel;
import pl.boniaszczuk.model.ProposalParams;
import pl.boniaszczuk.repository.ProposalRepository;
import pl.boniaszczuk.repository.ProposalStatusRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.boniaszczuk.exception.ReasonNotProvidedException.REASON_NOT_PROVIDED_ERROR;
import static pl.boniaszczuk.exception.UnableToAcceptProposalException.UNABLE_TO_ACCEPT_PROPOSAL;
import static pl.boniaszczuk.exception.UnableToDeleteProposalException.UNABLE_TO_DELETE_PROPOSAL;
import static pl.boniaszczuk.exception.UnableToPublishProposalException.UNABLE_TO_PUBLISH_PROPOSAL;
import static pl.boniaszczuk.exception.UnableToRejectProposalException.UNABLE_TO_REJECT_PROPOSAL;
import static pl.boniaszczuk.exception.UnableToUpdateProposalException.UNABLE_TO_UPDATE_PROPOSAL;
import static pl.boniaszczuk.exception.UnableToVerifyProposalException.UNABLE_TO_VERIFY_PROPOSAL;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProposalServiceTest {

    @Autowired
    private ProposalStatusService proposalStatusService;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalService proposalService;

    @BeforeEach
    void initialize(@Autowired ProposalStatusRepository proposalStatusRepository) {
        ProposalStatus createdProposalStatus = new ProposalStatus();
        createdProposalStatus.setIdentifier(ProposalStatusEnum.CREATED);
        proposalStatusRepository.save(createdProposalStatus);
        ProposalStatus deletedProposalStatus = new ProposalStatus();
        deletedProposalStatus.setIdentifier(ProposalStatusEnum.DELETED);
        proposalStatusRepository.save(deletedProposalStatus);
        ProposalStatus verifiedProposalStatus = new ProposalStatus();
        verifiedProposalStatus.setIdentifier(ProposalStatusEnum.VERIFIED);
        proposalStatusRepository.save(verifiedProposalStatus);
        ProposalStatus acceptedProposalStatus = new ProposalStatus();
        acceptedProposalStatus.setIdentifier(ProposalStatusEnum.ACCEPTED);
        proposalStatusRepository.save(acceptedProposalStatus);
        ProposalStatus rejectedProposalStatus = new ProposalStatus();
        rejectedProposalStatus.setIdentifier(ProposalStatusEnum.REJECTED);
        proposalStatusRepository.save(rejectedProposalStatus);
        ProposalStatus publishedProposalStatus = new ProposalStatus();
        publishedProposalStatus.setIdentifier(ProposalStatusEnum.PUBLISHED);
        proposalStatusRepository.save(publishedProposalStatus);
    }

    @Test
    void shouldCreateProposal() {
        // Given
        ProposalModel proposalModel = new ProposalModel();
        proposalModel.setName("TestName");
        proposalModel.setContent("TestContent");

        // When
        proposalService.createProposal(proposalModel);

        // Then
        Optional<Proposal> savedProposalOptional = proposalRepository.findByName("TestName");
        assertTrue(savedProposalOptional.isPresent(), "Expected saved proposal to be present");

        Proposal savedProposal = savedProposalOptional.get();

        assertEquals(proposalModel.getName(), savedProposal.getName());
        assertEquals(proposalModel.getContent(), savedProposal.getContent());
        assertEquals(ProposalStatusEnum.CREATED, savedProposal.getProposalStatus().getIdentifier());
        assertNotNull(savedProposal.getCreateDate());
    }

    @Test
    void shouldGetAllProposals() {
        // Given
        saveProposalWithStatus(ProposalStatusEnum.CREATED);
        saveProposalWithStatus(ProposalStatusEnum.VERIFIED);
        saveProposalWithStatus(ProposalStatusEnum.ACCEPTED);
        ProposalParams params = new ProposalParams();
        Pageable pageable = PageRequest.of(0, 3);

        // When
        Page<ProposalModel> result = proposalService.getAll(params, pageable);

        //Then
        assertNotNull(result);
        assertEquals(3, result.getContent().size());

        List<ProposalStatusEnum> expectedStatuses = Arrays.asList(ProposalStatusEnum.CREATED, ProposalStatusEnum.VERIFIED, ProposalStatusEnum.ACCEPTED);
        List<ProposalStatusEnum> actualStatuses = result.getContent().stream()
                .map(ProposalModel::getProposalStatus)
                .toList();
        assertTrue(actualStatuses.containsAll(expectedStatuses));
    }

    @Test
    void shouldDeleteProposal() {
        //given
        ActionReason deleteReason = new ActionReason("Some delete reason");
        saveProposalWithStatus(ProposalStatusEnum.CREATED);

        //when
        proposalService.deleteProposal(deleteReason, 1L);

        //then
        Optional<Proposal> deletedProposal = proposalRepository.findById(1L);
        assertTrue(deletedProposal.isPresent(), "Expected proposal to exist");
        assertEquals(ProposalStatusEnum.DELETED, deletedProposal.get().getProposalStatus().getIdentifier());
        assertEquals(deletedProposal.get().getDeleteReason(), deleteReason.reason());
    }

    @Test
    void shouldRejectProposal() {
        //given
        ActionReason rejectReason = new ActionReason("Some reject reason");
        saveProposalWithStatus(ProposalStatusEnum.VERIFIED);

        //when
        proposalService.rejectProposal(rejectReason, 1L);

        //then
        Optional<Proposal> rejectedProposal = proposalRepository.findById(1L);
        System.out.println(rejectedProposal.get().getProposalStatus().getIdentifier());
        assertTrue(rejectedProposal.isPresent(), "Expected proposal to exist");
        assertEquals(ProposalStatusEnum.REJECTED, rejectedProposal.get().getProposalStatus().getIdentifier());
        assertEquals(rejectedProposal.get().getRejectionReason(), rejectReason.reason());
    }

    @Test
    void shouldVerifyProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.CREATED);

        //when
        proposalService.verifyProposal(1L);

        //then
        Optional<Proposal> verifiedProposal = proposalRepository.findById(1L);
        assertTrue(verifiedProposal.isPresent(), "Expected proposal to exist");
        assertEquals(ProposalStatusEnum.VERIFIED, verifiedProposal.get().getProposalStatus().getIdentifier());
    }

    @Test
    void shouldAcceptProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.VERIFIED);

        //when
        proposalService.acceptProposal(1L);

        //then
        Optional<Proposal> acceptedProposal = proposalRepository.findById(1L);
        assertTrue(acceptedProposal.isPresent(), "Expected proposal to exist");
        assertEquals(ProposalStatusEnum.ACCEPTED, acceptedProposal.get().getProposalStatus().getIdentifier());
    }

    @Test
    void shouldPublishProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.ACCEPTED);

        //when
        proposalService.publishProposal(1L);

        //then
        Optional<Proposal> publishedProposal = proposalRepository.findById(1L);
        assertTrue(publishedProposal.isPresent(), "Expected proposal to exist");
        assertEquals(ProposalStatusEnum.PUBLISHED, publishedProposal.get().getProposalStatus().getIdentifier());
    }

    @Test
    void shouldThrowUnableToPublishProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.VERIFIED);

        //when,then
        UnableToPublishProposalException exception = assertThrows(UnableToPublishProposalException.class, () -> {
            proposalService.publishProposal(1L);
            ;
        });

        assertEquals(UNABLE_TO_PUBLISH_PROPOSAL, exception.getMessage());
    }

    @Test
    void shouldThrowUnableToAcceptProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.CREATED);

        //when,then
        UnableToAcceptProposalException exception = assertThrows(UnableToAcceptProposalException.class, () -> {
            proposalService.acceptProposal(1L);
            ;
        });
        assertEquals(UNABLE_TO_ACCEPT_PROPOSAL, exception.getMessage());
    }

    @Test
    void shouldThrowUnableToUpdateProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.ACCEPTED);
        ProposalModel proposalModel = new ProposalModel();
        proposalModel.setName("Name");

        //when,then
        UnableToUpdateProposalException exception = assertThrows(UnableToUpdateProposalException.class, () -> {
            proposalService.updateProposal(proposalModel);
            ;
        });
        assertEquals(UNABLE_TO_UPDATE_PROPOSAL, exception.getMessage());
    }

    @Test
    void shouldThrowUnableToVerifyProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.ACCEPTED);

        //when,then
        UnableToVerifyProposalException exception = assertThrows(UnableToVerifyProposalException.class, () -> {
            proposalService.verifyProposal(1L);
            ;
        });
        assertEquals(UNABLE_TO_VERIFY_PROPOSAL, exception.getMessage());
    }

    @Test
    void shouldThrowUnableToRejectProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.PUBLISHED);
        ActionReason rejectReason = new ActionReason("Some reject reason");

        //when,then
        UnableToRejectProposalException exception = assertThrows(UnableToRejectProposalException.class, () -> {
            proposalService.rejectProposal(rejectReason, 1L);
        });
        assertEquals(UNABLE_TO_REJECT_PROPOSAL, exception.getMessage());
    }

    @Test
    void shouldThrowUnableToDeleteProposal() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.PUBLISHED);
        ActionReason deleteReason = new ActionReason("Some delete reason");

        //when,then
        UnableToDeleteProposalException exception = assertThrows(UnableToDeleteProposalException.class, () -> {
            proposalService.deleteProposal(deleteReason, 1L);
        });
        assertEquals(UNABLE_TO_DELETE_PROPOSAL, exception.getMessage());
    }

    @Test
    void shouldThrowReasonNotProvided() {
        //given
        saveProposalWithStatus(ProposalStatusEnum.CREATED);
        ActionReason deleteReason = new ActionReason("");

        //when,then
        ReasonNotProvidedException exception = assertThrows(ReasonNotProvidedException.class, () -> {
            proposalService.deleteProposal(deleteReason, 1L);
        });
        assertEquals(REASON_NOT_PROVIDED_ERROR, exception.getMessage());
    }

    private void saveProposalWithStatus(ProposalStatusEnum proposalStatusEnum) {
        Proposal proposal = new Proposal();
        proposal.setName("Name");
        proposal.setContent("Content");
        proposal.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(proposalStatusEnum));
        proposalRepository.save(proposal);
    }
}
