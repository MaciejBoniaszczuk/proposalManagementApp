package pl.boniaszczuk.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pl.boniaszczuk.enumeration.ProposalStatusEnum;

import java.time.LocalDateTime;

@Data
public class ProposalModel {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Content is required.")
    private String content;

    private ProposalStatusEnum proposalStatus;

    private LocalDateTime createDate;

    private String rejectionReason;

    private String deleteReason;

    private String uniqueNumber;
}
