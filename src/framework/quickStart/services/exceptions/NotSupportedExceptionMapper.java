package framework.quickStart.services.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotSupportedExceptionMapper implements ExceptionMapper<NotSupportedException>{

	@Override
	public Response toResponse(NotSupportedException exception) {

		exception.printStackTrace();
		
		return Response.status(405) //Method Not Allowed
				.entity(exception.getMessage())
				.type("text/plain").build();
		
	}	
}