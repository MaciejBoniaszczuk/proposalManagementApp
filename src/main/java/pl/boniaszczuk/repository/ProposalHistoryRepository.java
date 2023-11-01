package pl.boniaszczuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.boniaszczuk.entity.ProposalHistory;

public interface ProposalHistoryRepository extends JpaRepository<ProposalHistory, Long> {
}
