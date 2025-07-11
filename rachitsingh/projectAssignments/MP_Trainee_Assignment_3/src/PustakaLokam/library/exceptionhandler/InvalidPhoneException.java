package PustakaLokam.library.exceptionhandler;

public class InvalidPhoneException extends Exception {
	@Override
	public String getMessage() {
		return "Phone should match exactly,10 digits number only!";
	}
}