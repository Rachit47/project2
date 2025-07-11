package library.exceptionhandler;

public class IssueOperationException extends Exception {

	private static final long serialVersionUID = 1L;

	public IssueOperationException(String message) {
		super(message);
	}

	public IssueOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}