package framework.quickStart.resourceAccessors;

public class ResourceUpdateAccessDeniedException extends RuntimeException {

    public ResourceUpdateAccessDeniedException(String message) {
    	super(message);
    }
 
    public ResourceUpdateAccessDeniedException(Throwable cause) {
    	super(cause);
    }
 
    public ResourceUpdateAccessDeniedException(String message, Throwable cause) {
    	super(message, cause);
    }
}
