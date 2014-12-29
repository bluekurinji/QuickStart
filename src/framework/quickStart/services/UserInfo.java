package framework.quickStart.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.spi.container.ResourceFilters;

import framework.quickStart.services.mediaType.JsonUtil;
import framework.quickStart.services.security.AuthFilter;

@Path("/userinfos")
@Produces("application/json")
@Consumes("application/json")
@ResourceFilters(AuthFilter.class)
public class UserInfo extends Service<java.util.UUID, framework.quickStart.businessObjects.UserInfo, framework.quickStart.dataAccessObjects.IUserInfo, framework.quickStart.resourceAccessors.UserInfo>{
			
	@Path("/myinfo")
	@GET
	public String getCurrentUserInfo(@Context UriInfo uriInfo)
	{
		String callBackFunctionName = uriInfo.getQueryParameters().getFirst(CALLBACK);	
		return makeJSONP(JsonUtil.convertToJson(getCurrentUser(), framework.quickStart.businessObjects.UserInfo.class, getCurrentUser()), callBackFunctionName);
	}
}