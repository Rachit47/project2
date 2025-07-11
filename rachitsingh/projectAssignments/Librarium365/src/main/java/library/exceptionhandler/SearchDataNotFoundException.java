package library.exceptionhandler;

public class SearchDataNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	private String data;
	public SearchDataNotFoundException(String data){
		this.data = data;
	}
	public String msg() {
		return "Email = "+ data +", Not registered in DataBase !!";
	}
}
