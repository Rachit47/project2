package PustakaLokam.library.utilities;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import PustakaLokam.library.exceptionhandler.InvalidEmailException;
import PustakaLokam.library.exceptionhandler.InvalidPhoneException;
import PustakaLokam.library.model.Member;

public class Validation {

	// default constructor
	public Validation() {
		
	}

	private void validateEmail(String email) throws InvalidEmailException {
		String regex = "^[\\w.-]+@gmail\\.com$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(email);
	    if(!matcher.matches()) {
	       throw new InvalidEmailException();
	    }  
	}
	private void validateMobile(Long phone) throws InvalidPhoneException{
		String regex = "^[6-9]\\d{9}$";
		String mobile = ""+phone;
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(mobile);
	    if(!matcher.matches()) {
	       throw new InvalidPhoneException();
	    } 
	}
	public void validate(Member member) throws InvalidEmailException, InvalidPhoneException{
		// email validations 
		String email = member.getEmail();
		validateEmail(email);
		// phone validations
		long phone = member.getMobile();
		validateMobile(phone);	
	}
}