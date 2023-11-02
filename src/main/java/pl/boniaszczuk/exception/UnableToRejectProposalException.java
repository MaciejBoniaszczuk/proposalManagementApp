package pl.boniaszczuk.exception;

public class UnableToRejectProposalException extends RuntimeException {
    public static final String UNABLE_TO_REJECT_PROPOSAL = "Only VERIFIED or ACCEPTED proposal can be reject";

    public UnableToRejectProposalException() {
        super(UNABLE_TO_REJECT_PROPOSAL);
    }
}
