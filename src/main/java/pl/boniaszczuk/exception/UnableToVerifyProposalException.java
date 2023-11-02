package pl.boniaszczuk.exception;

public class UnableToVerifyProposalException extends RuntimeException {
    public static final String UNABLE_TO_VERIFY_PROPOSAL = "Only CREATED proposal can be verified";

    public UnableToVerifyProposalException() {
        super(UNABLE_TO_VERIFY_PROPOSAL);
    }
}
