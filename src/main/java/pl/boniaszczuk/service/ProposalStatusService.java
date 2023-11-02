package pl.boniaszczuk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.boniaszczuk.entity.ProposalStatus;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;
import pl.boniaszczuk.repository.ProposalStatusRepository;

@Service
@AllArgsConstructor
public class ProposalStatusService {

    private final ProposalStatusRepository proposalStatusRepository;

    public ProposalStatus getProposalStatusByIdentifier(ProposalStatusEnum proposalStatusEnum) {
        return proposalStatusRepository.findByIdentifier(proposalStatusEnum)
                .orElseThrow(() -> new RuntimeException());
    }
}
