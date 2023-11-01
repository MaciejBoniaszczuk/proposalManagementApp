package pl.boniaszczuk.model;

import lombok.Data;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;

import java.util.List;

@Data
public class ProposalParams {

    private List<String> names;
    private List<ProposalStatusEnum> proposalStatuses;
}
