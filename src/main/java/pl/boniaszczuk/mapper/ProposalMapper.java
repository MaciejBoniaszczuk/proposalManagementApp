package pl.boniaszczuk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pl.boniaszczuk.entity.Proposal;
import pl.boniaszczuk.model.ProposalModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProposalMapper {
    @Mapping(target = "proposalStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    Proposal mapToEntity(ProposalModel proposalModel);

    @Mapping(target = "proposalStatus", source = "proposalStatus.identifier")
    ProposalModel mapToModel(Proposal proposal);
}
