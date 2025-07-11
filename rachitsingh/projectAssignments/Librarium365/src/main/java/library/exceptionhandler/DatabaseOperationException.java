package library.exceptionhandler;

public class DatabaseOperationException extends RuntimeException {
    private static final long serialVersionUID = 8393502545688876778L;

	public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
