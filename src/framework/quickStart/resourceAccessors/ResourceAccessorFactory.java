package framework.quickStart.resourceAccessors;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

import framework.quickStart.businessObjects.IBusinessEntity;

public class ResourceAccessorFactory  {
	
	private static Map<Class<? extends IBusinessEntity>, Class<? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>> resourceMap = new HashMap<Class<? extends IBusinessEntity>, Class<? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>>();
	
	public static <T extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>> T getResourceAccessor(Class<? extends IBusinessEntity> cls, framework.quickStart.businessObjects.UserInfo user)
	{
		try
		{
			T resource = (T)getResourceAccessorClass(cls).newInstance();
			resource.setCurrentUser(user);
			return resource;
		}
		catch(Exception ex)
		{
			throw new RuntimeException("Can not instantiate Resource for : " + cls.getCanonicalName(), ex);
		}
	}
	
    private static Class<? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>> getResourceAccessorClass(Class<? extends IBusinessEntity> cls)
    {				
    	if (resourceMap.containsKey(cls))
    		return resourceMap.get(cls);

    	try
    	{
    		String businessClassName = cls.getSimpleName();
    		Class<? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>> respourceAccessorClass = (Class<? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>>)Class.forName("framework.quickStart.resourceAccessors." + businessClassName);
    		registerResourceAccessor(cls, respourceAccessorClass);
    		
    		return respourceAccessorClass;
    	}
    	catch(Exception ex)
    	{
    		throw new RuntimeException("Can not find Resource Accessor for business class : " + cls.getCanonicalName());
    	}
    }
	
    public static void registerResourceAccessor(Class<? extends IBusinessEntity> key, Class<? extends ResourceAccessor<? extends Serializable, ? extends IBusinessEntity, ? extends framework.quickStart.dataAccessObjects.IDataAccessObject<? extends IBusinessEntity, ? extends Serializable>>> value)
    {
    	resourceMap.put(key, value);
    }
}
