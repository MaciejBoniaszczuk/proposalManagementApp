package pl.boniaszczuk.exception;

public class ReasonNotProvidedException extends RuntimeException {
    public static final String REASON_NOT_PROVIDED_ERROR = "You have to provide reason";

    public ReasonNotProvidedException() {
        super(REASON_NOT_PROVIDED_ERROR);
    }
}
