package framework.quickStart.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import framework.quickStart.businessObjects.IBusinessEntity;
import framework.quickStart.resourceAccessors.ResourceAccessor;

public class ServiceFactory {

	private static Map<Class<? extends IBusinessEntity>, Class<? extends Service<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>, ? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>>> map = new HashMap<Class<? extends IBusinessEntity>, Class<? extends Service<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>, ? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>>>();

	private static void registerMapping(
			Class<? extends IBusinessEntity> key,
			Class<? extends Service<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>, ? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>> value) {
		map.put(key, value);
	}

	public static <T extends Service<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>, ? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>> T getServiceInstance(
			Class<? extends IBusinessEntity> cls, SecurityContext securityContext) {

		try
		{
			T service = (T)getServiceClass(cls).newInstance();
			service.setSecurityContext(securityContext);
			return service;
		}
		catch(Exception ex)
		{
			throw new RuntimeException("Can not instantiate service for : " + cls.getCanonicalName(), ex);
		}
	}
	
    private static Class<? extends Service<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>, ? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>> getServiceClass(Class<? extends IBusinessEntity> cls)
    {				
    	if (map.containsKey(cls))
    		return map.get(cls);

    	try
    	{
    		String businessClassName = cls.getSimpleName();
    		Class<? extends Service<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>, ? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>> serviceClass = (Class<? extends Service<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>, ? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>>)Class.forName("framework.quickStart.services." + businessClassName);
    		registerMapping(cls, serviceClass);
    		
    		return serviceClass;
    	}
    	catch(Exception ex)
    	{
    		throw new RuntimeException("Can not find Service for business class : " + cls.getCanonicalName());
    	}
    }

}
