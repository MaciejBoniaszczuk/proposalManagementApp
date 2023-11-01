package pl.boniaszczuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.boniaszczuk.entity.ProposalStatus;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;

import java.util.Optional;

public interface ProposalStatusRepository extends JpaRepository<ProposalStatus, Long> {

    Optional<ProposalStatus> findByIdentifier(ProposalStatusEnum proposalStatusEnum);
}
