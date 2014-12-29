package framework.quickStart.services.exceptions;

public class InvalidMediaTypeException extends RuntimeException{

	
	public InvalidMediaTypeException(String message)
	{
		super(message);
	}
	
	public InvalidMediaTypeException(String message, Exception ex)
	{
		super(message, ex);
	}
}
