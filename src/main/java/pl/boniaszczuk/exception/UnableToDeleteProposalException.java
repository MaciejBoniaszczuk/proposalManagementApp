package pl.boniaszczuk.exception;

public class UnableToDeleteProposalException extends RuntimeException {
    public static final String UNABLE_TO_DELETE_PROPOSAL = "Only CREATED proposal can be deleted";

    public UnableToDeleteProposalException() {
        super(UNABLE_TO_DELETE_PROPOSAL);
    }
}
