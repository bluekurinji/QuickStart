package framework.quickStart.services.exceptions;

public class InternalFailureException extends RuntimeException {

	public InternalFailureException(String message)
	{
		super(message);
	}
	
	public InternalFailureException(Throwable ex)
	{
		super(ex);
	}
	
	public InternalFailureException(String message, Throwable ex)
	{
		super(message, ex);
	}
}
