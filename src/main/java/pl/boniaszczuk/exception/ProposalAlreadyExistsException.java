package pl.boniaszczuk.exception;

public class ProposalAlreadyExistsException extends RuntimeException {
    public ProposalAlreadyExistsException(String name) {
        super(String.format("Proposal with name: %s already exists", name));
    }
}
