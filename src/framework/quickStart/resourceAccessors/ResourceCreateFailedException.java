package framework.quickStart.resourceAccessors;

public class ResourceCreateFailedException extends RuntimeException {
 
    public ResourceCreateFailedException(String message) {
    	super(message);
    }
 
    public ResourceCreateFailedException(Throwable cause) {
    	super(cause);
    }
 
    public ResourceCreateFailedException(String message, Throwable cause) {
    	super(message, cause);
    }
}
