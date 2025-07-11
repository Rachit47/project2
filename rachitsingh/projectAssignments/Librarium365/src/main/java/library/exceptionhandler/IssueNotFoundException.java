package library.exceptionhandler;

public class IssueNotFoundException extends Exception {
    private static final long serialVersionUID = -8638957598544177678L;

	public IssueNotFoundException(String message) {
        super(message);
    }
}