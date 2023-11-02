package pl.boniaszczuk.exception;

public class UnableToPublishProposalException extends RuntimeException {

    public static final String UNABLE_TO_PUBLISH_PROPOSAL = "Only ACCEPTED proposal can be published";
    public UnableToPublishProposalException() {
        super(UNABLE_TO_PUBLISH_PROPOSAL);
    }
}
