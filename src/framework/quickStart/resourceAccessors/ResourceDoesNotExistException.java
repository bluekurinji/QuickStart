package framework.quickStart.resourceAccessors;

public class ResourceDoesNotExistException extends RuntimeException {
 
    public ResourceDoesNotExistException(String message) {
    	super(message);
    }
 
    public ResourceDoesNotExistException(Throwable cause) {
    	super(cause);
    }
 
    public ResourceDoesNotExistException(String message, Throwable cause) {
    	super(message, cause);
    }
}
