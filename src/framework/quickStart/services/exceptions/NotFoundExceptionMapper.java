package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException>{

	@Override
	public Response toResponse(NotFoundException exception) {

		exception.printStackTrace();
		
		return Response.status(Response.Status.NOT_FOUND)
				.entity(exception.getMessage())
				.type("text/plain").build();
		
	}	
}
