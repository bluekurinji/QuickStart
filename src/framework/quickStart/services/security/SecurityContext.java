package framework.quickStart.services.security;

import java.security.Principal;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import framework.quickStart.services.security.CurrentUser;

@Provider
public class SecurityContext implements javax.ws.rs.core.SecurityContext {
	
	CurrentUser currentUser;
	
	public SecurityContext(framework.quickStart.businessObjects.UserInfo userInfo)
	{
		if (userInfo != null)
			currentUser = new CurrentUser(userInfo);
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return currentUser;
	}

	@Override
	public boolean isSecure() {
		return currentUser != null;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		 throw new WebApplicationException(); // not supported yet
	}
}
