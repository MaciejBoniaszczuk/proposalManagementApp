package pl.boniaszczuk.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import pl.boniaszczuk.entity.Proposal;
import pl.boniaszczuk.entity.ProposalStatus;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;
import pl.boniaszczuk.mapper.ProposalMapper;
import pl.boniaszczuk.model.ActionReason;
import pl.boniaszczuk.model.ProposalModel;
import pl.boniaszczuk.repository.ProposalRepository;
import pl.boniaszczuk.repository.ProposalStatusRepository;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ProposalServiceTest {

    @Autowired
    private ProposalStatusService proposalStatusService;

    @Autowired
    private ProposalRepository proposalRepository;

    @MockBean
    private ProposalHistoryService proposalHistoryService;

    @Mock
    private ProposalMapper proposalMapper;

    @Autowired
    private ProposalService proposalService;

    @BeforeAll
    static void initialize(@Autowired ProposalStatusRepository proposalStatusRepository) {
        ProposalStatus createdProposalStatus = new ProposalStatus();
        createdProposalStatus.setIdentifier(ProposalStatusEnum.CREATED);
        proposalStatusRepository.save(createdProposalStatus);
        ProposalStatus deletedProposalStatus = new ProposalStatus();
        deletedProposalStatus.setIdentifier(ProposalStatusEnum.DELETED);
        proposalStatusRepository.save(deletedProposalStatus);
    }

//    @Test
//    void testCreateProposal() {
//        // Given
//        ProposalModel proposalModel = new ProposalModel();
//        proposalModel.setName("TestName");
//        proposalModel.setContent("TestContent");
//
//
//        // When
//        proposalService.createProposal(proposalModel);
//
//        // Then
//        Optional<Proposal> savedProposalOptional = proposalRepository.findByName("TestName");
//        assertTrue(savedProposalOptional.isPresent(), "Expected saved proposal to be present");
//
//        Proposal savedProposal = savedProposalOptional.get();
//
//        assertEquals(proposalModel.getName(), savedProposal.getName());
//        assertEquals(proposalModel.getContent(), savedProposal.getContent());
//        verify(proposalStatusService).getProposalStatusByIdentifier(ProposalStatusEnum.CREATED);
//        verify(proposalHistoryService).createInitialHistoryForProposal(savedProposal);
//    }
//
//    @Test
//    void testDeleteProposal() {
//        //given
//        ActionReason deleteReason = new ActionReason("Some reason");
//        saveProposalInDb();
//
//        //when
//        proposalService.deleteProposal(deleteReason, 1L);
//
//        //then
//        Optional<Proposal> deletedProposal = proposalRepository.findById(1L);
//        assertTrue(deletedProposal.isPresent(), "Expected proposal to exist");
//        assertEquals(ProposalStatusEnum.DELETED, deletedProposal.get().getProposalStatus().getIdentifier());
//    }
//
//    private void saveProposalInDb() {
//        Proposal proposal = new Proposal();
//        proposal.setName("Name");
//        proposal.setContent("Content");
//        proposal.setProposalStatus(proposalStatusService.getProposalStatusByIdentifier(ProposalStatusEnum.CREATED));
//        proposalRepository.save(proposal);
//    }

}
