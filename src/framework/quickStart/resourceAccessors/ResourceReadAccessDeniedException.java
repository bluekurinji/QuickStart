package framework.quickStart.resourceAccessors;

public class ResourceReadAccessDeniedException extends RuntimeException {
 
    public ResourceReadAccessDeniedException(String message) {
    	super(message);
    }
 
    public ResourceReadAccessDeniedException(Throwable cause) {
    	super(cause);
    }
 
    public ResourceReadAccessDeniedException(String message, Throwable cause) {
    	super(message, cause);
    }
}