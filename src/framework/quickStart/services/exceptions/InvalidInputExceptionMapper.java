package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException>{

	@Override
	public Response toResponse(InvalidInputException exception) {

		exception.printStackTrace();
		
		return Response.status(Response.Status.FORBIDDEN)
				.entity(exception.getMessage())
				.type("text/plain").build();
		
	}	
}
