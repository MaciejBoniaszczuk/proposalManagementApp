package pl.boniaszczuk.exception;

public class UnableToAcceptProposalException extends RuntimeException {

    public static final String UNABLE_TO_ACCEPT_PROPOSAL = "Only VERIFIED proposal can be accepted";

    public UnableToAcceptProposalException() {
        super(UNABLE_TO_ACCEPT_PROPOSAL);
    }
}
