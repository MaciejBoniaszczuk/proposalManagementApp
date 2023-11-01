package pl.boniaszczuk.exception;

public class UnableToPublishProposal extends RuntimeException {
    public UnableToPublishProposal(String message) {
        super(message);
    }
}
