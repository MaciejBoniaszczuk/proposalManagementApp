package pl.boniaszczuk.exception;

public class UnableToUpdateProposalException extends RuntimeException {
    public static final String UNABLE_TO_UPDATE_PROPOSAL = "Proposal can be updated only for CREATED and VERIFIED proposals.";

    public UnableToUpdateProposalException() {
        super(UNABLE_TO_UPDATE_PROPOSAL);
    }
}
