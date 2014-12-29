package framework.quickStart.services.exceptions;

@SuppressWarnings("serial")
public class NotSupportedException extends RuntimeException {

	public NotSupportedException(String message)
	{
		super(message);
	}
	
	public NotSupportedException(String message, Throwable ex)
	{
		super(message, ex);
	}
}
