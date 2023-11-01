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
@Table(name = "PROPOSAL", schema = "proposal_manager")
@Data
public class Proposal {

    @Id
    @SequenceGenerator(name = "PROPOSAL_ID_SEQ", sequenceName = "PROPOSAL_MANAGER.PROPOSAL_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROPOSAL_ID_SEQ")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CONTENT")
    private String content;
    @ManyToOne
    @JoinColumn(name = "PROPOSAL_STATUS_ID")
    private ProposalStatus proposalStatus;
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    @Column(name = "REJECTION_REASON")
    private String rejectionReason;
    @Column(name = "DELETE_REASON")
    private String deleteReason;
    @Column(name = "UNIQUE_NUMBER")
    private String uniqueNumber;
}
