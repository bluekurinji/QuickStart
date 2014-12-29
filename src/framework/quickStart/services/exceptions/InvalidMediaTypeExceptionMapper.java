package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidMediaTypeExceptionMapper implements ExceptionMapper<InvalidMediaTypeException>{

	@Override
	public Response toResponse(InvalidMediaTypeException exception) {
		
		exception.printStackTrace();

		return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
				.entity(exception.getMessage())
				.type("text/plain").build();
		
	}	
}