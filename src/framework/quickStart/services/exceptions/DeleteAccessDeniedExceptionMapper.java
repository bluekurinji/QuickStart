package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import framework.quickStart.resourceAccessors.ResourceDeleteAccessDeniedException;

@Provider
public class DeleteAccessDeniedExceptionMapper implements ExceptionMapper<ResourceDeleteAccessDeniedException>
{
	@Override
	public Response toResponse(ResourceDeleteAccessDeniedException exception) {
		
		exception.printStackTrace();

		return Response.status(Response.Status.FORBIDDEN)
				.entity(exception.getMessage())
				.type("text/plain").build();
	}
}