package framework.quickStart.services.exceptions;

public class InvalidInputException extends RuntimeException{

	public InvalidInputException()
	{
		super();
	}
	
	public InvalidInputException(String message)
	{
		super(message);
	}
	
	public InvalidInputException(String message, Throwable ex)
	{
		super(message, ex);
	}
}