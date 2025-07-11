package PustakaLokam.library.exceptionhandler;

public class InvalidEmailException extends Exception {
	@Override
	public String getMessage() {
		return "Email is not in proper format, it should end with @gmail.com !";
	}
}