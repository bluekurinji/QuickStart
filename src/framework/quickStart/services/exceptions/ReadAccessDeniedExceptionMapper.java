package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import framework.quickStart.resourceAccessors.ResourceReadAccessDeniedException;

@Provider
public class ReadAccessDeniedExceptionMapper 
	implements ExceptionMapper<ResourceReadAccessDeniedException>
{
	@Override
	public Response toResponse(ResourceReadAccessDeniedException exception) {

		exception.printStackTrace();
		
		return Response.status(Response.Status.FORBIDDEN)
				.entity(exception.getMessage())
				.type("text/plain").build();
	}
}
