package pl.boniaszczuk.exception;

public class ReasonNotProvidedException extends RuntimeException {
    public ReasonNotProvidedException(String message) {
        super(message);
    }
}
