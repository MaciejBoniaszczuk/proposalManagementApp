package pl.boniaszczuk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "PROPOSAL_HISTORY", schema = "proposal_manager")
@Data
public class ProposalHistory {
    @Id
    @SequenceGenerator(name = "PROPOSAL_HISTORY_ID_SEQ", sequenceName = "PROPOSAL_MANAGER.PROPOSAL_HISTORY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROPOSAL_HISTORY_ID_SEQ")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PROPOSAL_ID", referencedColumnName = "ID")
    private Proposal proposal;
    @ManyToOne
    @JoinColumn(name = "FROM_PROPOSAL_STATUS_ID")
    private ProposalStatus fromStatus;
    @ManyToOne
    @JoinColumn(name = "TO_PROPOSAL_STATUS_ID")
    private ProposalStatus toStatus;
    @Column(name = "CHANGE_DATE")
    private LocalDateTime changeDate;
}
