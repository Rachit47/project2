package library.exceptionhandler;

public class MemberNotFoundException extends Exception {

    private static final long serialVersionUID = 8948278412425356967L;

	public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}