package library.exceptionhandler;

public class InvalidPhoneException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Phone should match exactly,10 digits number only!";
	}
}