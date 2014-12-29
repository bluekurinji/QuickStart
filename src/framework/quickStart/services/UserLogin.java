package framework.quickStart.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.jersey.spi.container.ResourceFilters;

import framework.quickStart.services.security.AuthFilter;

@Path("/userlogins")
@Produces("application/json")
@Consumes("application/json")
@ResourceFilters(AuthFilter.class)
public class UserLogin extends Service<String, framework.quickStart.businessObjects.UserLogin, framework.quickStart.dataAccessObjects.IUserLogin, framework.quickStart.resourceAccessors.UserLogin>{

}