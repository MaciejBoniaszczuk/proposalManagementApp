package pl.boniaszczuk.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.boniaszczuk.entity.Proposal;
import pl.boniaszczuk.entity.ProposalStatus;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;
import pl.boniaszczuk.model.ProposalModel;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProposalMapperTest {
    private final ProposalMapper mapper = Mappers.getMapper(ProposalMapper.class);

    @Test
    void shouldMapToEntity() {
        // given
        ProposalModel proposalModel = new ProposalModel();
        proposalModel.setName("TestName");
        proposalModel.setContent("TestContent");
        proposalModel.setCreateDate(LocalDate.of(2023, 11, 3).atStartOfDay());
        proposalModel.setRejectionReason("rejectionReason");
        proposalModel.setDeleteReason("deleteReason");
        proposalModel.setUniqueNumber("12345");

        // when
        Proposal entity = mapper.mapToEntity(proposalModel);

        // then
        assertEquals(proposalModel.getName(), entity.getName());
        assertEquals(proposalModel.getContent(), entity.getContent());
        assertEquals(proposalModel.getRejectionReason(), entity.getRejectionReason());
        assertEquals(proposalModel.getDeleteReason(), entity.getDeleteReason());
        assertEquals(proposalModel.getUniqueNumber(), entity.getUniqueNumber());
        assertEquals(proposalModel.getCreateDate(), entity.getCreateDate());

    }

    @Test
    void shouldMapToModel() {
        // given
        Proposal proposal = new Proposal();
        proposal.setName("TestName");
        proposal.setContent("TestContent");
        ProposalStatus status = new ProposalStatus();
        status.setIdentifier(ProposalStatusEnum.VERIFIED);
        proposal.setProposalStatus(status);
        proposal.setCreateDate(LocalDate.of(2023, 11, 3).atStartOfDay());
        proposal.setRejectionReason("rejectionReason");
        proposal.setDeleteReason("deleteReason");
        proposal.setUniqueNumber("12345");

        // when
        ProposalModel model = mapper.mapToModel(proposal);

        // then
        assertEquals(proposal.getName(), model.getName());
        assertEquals(proposal.getContent(), model.getContent());
        assertEquals(proposal.getProposalStatus().getIdentifier(), model.getProposalStatus());
        assertEquals(proposal.getCreateDate(), model.getCreateDate());
        assertEquals(proposal.getRejectionReason(), model.getRejectionReason());
        assertEquals(proposal.getDeleteReason(), model.getDeleteReason());
        assertEquals(proposal.getUniqueNumber(), model.getUniqueNumber());
    }
}
