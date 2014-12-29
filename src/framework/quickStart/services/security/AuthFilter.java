package framework.quickStart.services.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

import framework.quickStart.services.exceptions.InternalFailureException;

@Provider
public class AuthFilter implements ResourceFilter{

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return new ContainerRequestFilter() {
            @Override
            public ContainerRequest filter(ContainerRequest request) {
            	           	
            	Cookie sessionCookie = request.getCookies().get("sessionId");
            	if (sessionCookie == null)
            		throw new WebApplicationException(Status.UNAUTHORIZED);
            	
            	String sessionId = sessionCookie.getValue();
            	if ((sessionId == null) || (sessionId.trim().length() == 0))
            		throw new WebApplicationException(Status.UNAUTHORIZED);
            	
            	framework.quickStart.resourceAccessors.UserLogin userLoginAccessor = new framework.quickStart.resourceAccessors.UserLogin();
            	framework.quickStart.businessObjects.UserLogin userLogin = userLoginAccessor.getResource(sessionId);
            	if (userLogin == null)
            		throw new WebApplicationException(Status.UNAUTHORIZED);
            	
            	java.util.UUID userId = userLogin.getUserId();
            	
            	framework.quickStart.resourceAccessors.UserInfo userInfoAccessor = new framework.quickStart.resourceAccessors.UserInfo();
            	framework.quickStart.businessObjects.UserInfo userInfo = userInfoAccessor.getResource(userId);
            	if (userInfo == null)
            		throw new InternalFailureException("Inconsistent data !!! user id is misisng in userinfo. The id is " + userId);
            	
            	request.setSecurityContext(new SecurityContext(userInfo));
            	return request;
            }
        };
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return new ContainerResponseFilter() {
			@Override
			public ContainerResponse filter(ContainerRequest request,
					ContainerResponse response) {
				return response;
			} 
		};
	}
}
