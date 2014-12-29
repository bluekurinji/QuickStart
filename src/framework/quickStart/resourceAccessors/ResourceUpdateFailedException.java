package framework.quickStart.resourceAccessors;

public class ResourceUpdateFailedException extends RuntimeException {

    public ResourceUpdateFailedException(String message) {
    	super(message);
    }
 
    public ResourceUpdateFailedException(Throwable cause) {
    	super(cause);
    }
 
    public ResourceUpdateFailedException(String message, Throwable cause) {
    	super(message, cause);
    }
}
