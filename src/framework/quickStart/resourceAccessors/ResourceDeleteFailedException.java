package framework.quickStart.resourceAccessors;

public class ResourceDeleteFailedException extends RuntimeException {
 
    public ResourceDeleteFailedException(String message) {
    	super(message);
    }
 
    public ResourceDeleteFailedException(Throwable cause) {
    	super(cause);
    }
 
    public ResourceDeleteFailedException(String message, Throwable cause) {
    	super(message, cause);
    }
}
