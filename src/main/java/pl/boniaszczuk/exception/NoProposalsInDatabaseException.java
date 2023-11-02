package pl.boniaszczuk.exception;

public class NoProposalsInDatabaseException extends RuntimeException {
    private static final String NO_PROPOSALS_IN_DATABASE_ERROR = "There are no proposals in database";

    public NoProposalsInDatabaseException() {
        super(NO_PROPOSALS_IN_DATABASE_ERROR);
    }
}
