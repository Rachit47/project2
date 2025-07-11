package library.exceptionhandler;

public class InvalidBookDataException extends RuntimeException {
    private static final long serialVersionUID = 2699690538651315185L;

	public InvalidBookDataException(String message) {
        super(message);
    }
}