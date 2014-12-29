package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalFailureExceptionMapper implements ExceptionMapper<InternalFailureException>
{
	@Override
	public Response toResponse(InternalFailureException exception) {

		exception.printStackTrace();
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Oops !!!")
				.type("text/plain").build();
	}
}
