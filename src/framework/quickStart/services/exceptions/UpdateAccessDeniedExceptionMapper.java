package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import framework.quickStart.resourceAccessors.ResourceUpdateAccessDeniedException;

@Provider
public class UpdateAccessDeniedExceptionMapper implements ExceptionMapper<ResourceUpdateAccessDeniedException>
{
	@Override
	public Response toResponse(ResourceUpdateAccessDeniedException exception) {

		exception.printStackTrace();
		
		return Response.status(Response.Status.FORBIDDEN)
				.entity(exception.getMessage())
				.type("text/plain").build();
	}
}