package pl.boniaszczuk.model;

import io.swagger.v3.oas.annotations.Hidden;
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

    @Hidden
    private ProposalStatusEnum proposalStatus;
    @Hidden
    private LocalDateTime createDate;
    @Hidden
    private String rejectionReason;
    @Hidden
    private String deleteReason;
    @Hidden
    private String uniqueNumber;
}
