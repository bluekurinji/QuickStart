package framework.quickStart.services;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import framework.quickStart.businessObjects.IBusinessEntity;
import framework.quickStart.resourceAccessors.ResourceAccessor;
import framework.quickStart.resourceAccessors.ResourceAccessorFactory;
import framework.quickStart.services.exceptions.InternalFailureException;
import framework.quickStart.services.exceptions.NotFoundException;
import framework.quickStart.services.mediaType.JsonUtil;
import framework.quickStart.services.security.CurrentUser;

import javax.ws.rs.core.SecurityContext;

@Produces("application/json")
@Consumes("application/json")
public abstract class Service<ID extends Serializable, 
						BUSINESSOBJECT extends IBusinessEntity, 
						DATAACCESSOBJECT extends framework.quickStart.dataAccessObjects.IDataAccessObject<BUSINESSOBJECT, ID>,
						RESOURCEACCESSOR extends ResourceAccessor<ID, BUSINESSOBJECT, DATAACCESSOBJECT>> {
	
	@Context
	protected SecurityContext securityContext;	
	protected static final String CALLBACK = "callback";	
	protected Class<BUSINESSOBJECT> businessObjectClass = (Class<BUSINESSOBJECT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];;
	private framework.quickStart.businessObjects.UserInfo userInfo;
	
	public Service()
	{

	}
		
	public void validateResourceToBeCreated(BUSINESSOBJECT businessObject)
	{
		//TODO : to be used again
		//throw new UnsupportedOperationException("To be implemented");
	}
	
	protected framework.quickStart.businessObjects.UserInfo getCurrentUser()
	{		
		if (securityContext != null)
		{
			Principal principal = securityContext.getUserPrincipal();
			if (principal != null)
				userInfo = ((CurrentUser)principal).getUser();
		}
	
		return userInfo;
	}
	
	public void setSecurityContext(SecurityContext securityContext)
	{
		this.securityContext = securityContext;
	}
		
	protected java.util.UUID getCurrentEndUserId()
	{		
		if (getCurrentUser() == null)
			return null;
		
		return getCurrentUser().getUserId();
	}

	public BUSINESSOBJECT getResourceObject(ID id)
	{
		BUSINESSOBJECT businessObject = null;
		
		try
		{
			RESOURCEACCESSOR resourceAccessor = ResourceAccessorFactory.getResourceAccessor(businessObjectClass, getCurrentUser());
			businessObject = resourceAccessor.getResource(id);
		}
		catch(Exception exp)
		{
			throw new InternalFailureException(exp);
		}
		
		if (businessObject == null)
			throw new NotFoundException("Could not find resource with id " + id);
		
	    return businessObject;
	}
	
	
	@Path("{id}")
	@GET
	public String getResource(@PathParam("id") ID id, @QueryParam(CALLBACK) String callBackFunctionName)
	{
	    return makeJSONP(getResourceObject(id), callBackFunctionName);
	}

	
	@Path("{id}")
	@DELETE
	public void deleteResource(@PathParam("id") ID id)
	{		
		RESOURCEACCESSOR resourceAccessor = ResourceAccessorFactory.getResourceAccessor(businessObjectClass, getCurrentUser());
		boolean isFoundAndDeleted = resourceAccessor.deleteResource(id);

		if (!isFoundAndDeleted)
			throw new NotFoundException("No resource found to delete");
	}
	
	public List<BUSINESSOBJECT> getAllResourceObjects(Map<String, List<String>> params)		
	{				
		RESOURCEACCESSOR resourceAccessor = ResourceAccessorFactory.getResourceAccessor(businessObjectClass, getCurrentUser());
		List<BUSINESSOBJECT> resourceList = resourceAccessor.getAllResource(params);
		
		if (resourceList.size() == 0)
			throw new NotFoundException("No resource found");
		
		return resourceList;
	}
	
	@GET
	public String getAllResource(@Context UriInfo uriInfo)		
	{	
		String callBackFunctionName = uriInfo.getQueryParameters().getFirst(CALLBACK);	
		Map<String, List<String>> searchParams = new HashMap<String, List<String>>();
		
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
		Iterator<String> itr = queryParams.keySet().iterator();
		while(itr.hasNext())
		{
			String key = itr.next();
			if (key.equals(CALLBACK))
				continue;
			
			searchParams.put(key.toLowerCase(), queryParams.get(key));
		}
				
	    return makeJSONP(getAllResourceObjects(searchParams), callBackFunctionName);
	}
		
	@POST
	public String createResource(String requestMessageBody, @QueryParam(CALLBACK) String callBackFunctionName)
	{
	    BUSINESSOBJECT resource = JsonUtil.convertToBusinessObject(requestMessageBody, false, businessObjectClass, getCurrentUser());	    
	    validateResourceToBeCreated(resource);
	    
		RESOURCEACCESSOR resourceAccessor = ResourceAccessorFactory.getResourceAccessor(businessObjectClass, getCurrentUser());
		resource = resourceAccessor.createResource(resource);
		
	    return makeJSONP(resource, callBackFunctionName);
	}
	
	@PUT
	public void updateResource(String requestMessageBody)
	{
		BUSINESSOBJECT resource = JsonUtil.convertToBusinessObject(requestMessageBody, true, businessObjectClass, getCurrentUser());
		validateResourceToBeCreated(resource);
		
		RESOURCEACCESSOR resourceAccessor = ResourceAccessorFactory.getResourceAccessor(businessObjectClass, getCurrentUser());
		resourceAccessor.updateResource(resource);
	}
	
	protected String makeJSONP(List<BUSINESSOBJECT> resourceList, String callBackFunctionName)
	{
		String jsonResult =  JsonUtil.convertToJson(resourceList, businessObjectClass, getCurrentUser());	
		if (callBackFunctionName == null)
			return jsonResult;
		
	    return makeJSONP(jsonResult, callBackFunctionName);
	}
	
	protected String makeJSONP(BUSINESSOBJECT resource, String callBackFunctionName)
	{
		String jsonResult =  JsonUtil.convertToJson(resource, businessObjectClass, getCurrentUser());	
		if (callBackFunctionName == null)
			return jsonResult;
		
	    return makeJSONP(jsonResult, callBackFunctionName);
	}
	
	protected String makeJSONP(String jsonObjectAsString, String callBackFunctionName)
	{		
		if (callBackFunctionName == null)
			return jsonObjectAsString;
		
		StringBuilder sb = new StringBuilder();
		sb.append(callBackFunctionName).append("(").append(jsonObjectAsString).append(");");			
	    return sb.toString();	
	}

}
