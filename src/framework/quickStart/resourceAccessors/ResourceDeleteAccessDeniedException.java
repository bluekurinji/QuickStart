package framework.quickStart.resourceAccessors;

public class ResourceDeleteAccessDeniedException extends RuntimeException {
 
    public ResourceDeleteAccessDeniedException(String message) {
    	super(message);
    }
 
    public ResourceDeleteAccessDeniedException(Throwable cause) {
    	super(cause);
    }
 
    public ResourceDeleteAccessDeniedException(String message, Throwable cause) {
    	super(message, cause);
    }
}
