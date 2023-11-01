package pl.boniaszczuk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;

@Entity
@Table(name = "PROPOSAL_STATUS", schema = "proposal_manager")
@Data
public class ProposalStatus {

    @Id
    @SequenceGenerator(name = "PROPOSAL_STATUS_ID_SEQ", sequenceName = "PROPOSAL_MANAGER.PROPOSAL_STATUS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROPOSAL_STATUS_ID_SEQ")
    private Long id;
    @Column(name = "TECHNICAL_IDENTIFIER")
    @Enumerated(EnumType.STRING)
    private ProposalStatusEnum identifier;
}
