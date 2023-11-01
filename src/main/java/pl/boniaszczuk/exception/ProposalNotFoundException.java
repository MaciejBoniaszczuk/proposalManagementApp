package pl.boniaszczuk.exception;

public class ProposalNotFoundException extends RuntimeException {

    private static final String PROPOSAL_NOT_FOUND = "Proposal not found";

    public ProposalNotFoundException() {
        super(PROPOSAL_NOT_FOUND);
    }
}
