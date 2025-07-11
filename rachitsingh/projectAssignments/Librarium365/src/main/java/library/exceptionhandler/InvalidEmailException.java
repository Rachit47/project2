package library.exceptionhandler;


public class InvalidEmailException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Email is not in proper format, it should end with @gmail.com !";
	}
}