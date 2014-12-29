package framework.quickStart.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import framework.quickStart.services.exceptions.InternalFailureException;
import framework.quickStart.services.exceptions.InvalidInputException;
import framework.quickStart.util.Properties;
import framework.quickStart.util.StringUtil;

@Path("/")
public class Session {

	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Path("/singup")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signupNewUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("loginName") String loginName, @FormParam("password") String password, @FormParam("cpassword") String cpassword)
	{
		if (StringUtil.isNullorEmpty(firstName))
			throw new InvalidInputException("First Name is empty");

		if (StringUtil.isNullorEmpty(lastName))
			throw new InvalidInputException("Last Name is empty");
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(loginName);
		if (StringUtil.isNullorEmpty(loginName) || (!matcher.matches()))
			throw new InvalidInputException("Invalid email id");		
		
		if (StringUtil.isNullorEmpty(password))
			throw new InvalidInputException("Invalid password");	
		
		if (!password.equals(cpassword))
			throw new InvalidInputException("Confirm password is not matching");
		
		framework.quickStart.businessObjects.UserInfo userInfo = new framework.quickStart.businessObjects.UserInfo();
		userInfo.setFirstName(firstName);
		userInfo.setLastName(lastName);
		userInfo.setPassword(password);
		userInfo.setLoginName(loginName);
		userInfo.setPostalCode1("ABC");
		userInfo.setPostalCode2("ABC");
		userInfo.setPostalCode3("ABC");
		userInfo.setLatitude1(2.0);
		userInfo.setLatitude2(2.0);
		userInfo.setLatitude3(2.0);
		userInfo.setLongitude1(3.0);
		userInfo.setLongitude2(3.0);
		userInfo.setLongitude3(3.0);
		
		framework.quickStart.resourceAccessors.UserInfo userInfoAccessor = new framework.quickStart.resourceAccessors.UserInfo();
		userInfo = userInfoAccessor.createResource(userInfo);
		
		Session session = new Session();
		return session.buildValidLoginResponse(userInfo);
	}
	
	@POST
	@Path("/logout")
	public Response logout(@CookieParam("sessionId") String sessionId)
	{
		if (sessionId == null)
			throw new InvalidInputException("Invalid session id");
		
		return Response.ok().header("Set-Cookie", "sessionId=;expires=Thu, 01 Jan 1970 00:00:00 GMT;HttpOnly").build();
	}
	
	@POST
	public Response authenticate(@FormParam("loginName") String loginName, @FormParam("password") String password)
	{
		if ((loginName == null) || (loginName.trim().length() == 0) || (password == null) || (password.trim().length() == 0))
			return Response.status(Status.UNAUTHORIZED).build();
			
		
		framework.quickStart.resourceAccessors.UserInfo userInfoAccessor = new framework.quickStart.resourceAccessors.UserInfo();
		framework.quickStart.businessObjects.UserInfo userInfo = userInfoAccessor.authenticate(loginName, password);
		
		if (userInfo == null)
			return Response.status(Status.UNAUTHORIZED).build();
		
		return buildValidLoginResponse(userInfo);
	}
	
	void saveSessionData(String sesisonId, Date time, framework.quickStart.businessObjects.UserInfo userInfo)
	{
		framework.quickStart.businessObjects.UserLogin userLogin = new framework.quickStart.businessObjects.UserLogin();
		userLogin.setLoginName(userInfo.getLoginName());
		userLogin.setUserId(userInfo.getUserId());
		userLogin.setSessionId(sesisonId);
		userLogin.setAuthTokenCreationTime(time);
		
		framework.quickStart.resourceAccessors.UserLogin userLoginAccessor = new framework.quickStart.resourceAccessors.UserLogin();
		userLoginAccessor.createResource(userLogin);
	}
	
	public Response buildValidLoginResponse(framework.quickStart.businessObjects.UserInfo userInfo)
	{
		Date time = new Date(); 
		String sesisonId = buildSessionId(time, userInfo);
		
		NewCookie sessionCookie = new NewCookie("sessionId", sesisonId);
		
		saveSessionData(sesisonId, time, userInfo);
		URI uri;
		try {
			uri = new URI( Properties.getUIRootPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new InternalFailureException("Invalid home page uri");
		}
		
		return Response.seeOther(uri).header("Set-Cookie", sessionCookie.toString() + ";HttpOnly").build();
	}
	
	String buildSessionId(Date time, framework.quickStart.businessObjects.UserInfo userInfo)
	{
		//TODO need to create a HASH code of userid:loginName:sessionUUID:sessioncreationTime to construct authTokenId
		return java.util.UUID.randomUUID().toString();
	}

}
