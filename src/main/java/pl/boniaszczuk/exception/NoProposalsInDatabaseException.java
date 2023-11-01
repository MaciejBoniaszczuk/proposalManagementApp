package pl.boniaszczuk.exception;

public class NoProposalsInDatabaseException extends RuntimeException {
    public NoProposalsInDatabaseException(String message) {
        super(message);
    }
}
