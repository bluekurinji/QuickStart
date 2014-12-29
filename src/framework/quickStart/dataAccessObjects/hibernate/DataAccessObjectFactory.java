package framework.quickStart.dataAccessObjects.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import framework.quickStart.dataAccessObjects.IDataAccessObject;
import framework.quickStart.dataAccessObjects.IDataAccessObjectsFactory;
import framework.quickStart.dataAccessObjects.IDatabaseSession;

public class DataAccessObjectFactory implements IDataAccessObjectsFactory {
	
	private static Map<Class<? extends IDataAccessObject<?, ? extends Serializable>>, Class<? extends DataAccessObject<?, ? extends Serializable>>> daoMap = new HashMap<Class<? extends IDataAccessObject<?, ? extends Serializable>>, Class<? extends DataAccessObject<?, ? extends Serializable>>>();	
	
    public <S, ID extends Serializable, T extends IDataAccessObject<S, ID>> T getDAO(Class<T> cls, IDatabaseSession dbSession)
    {    	
		if (dbSession == null)
			throw new IllegalArgumentException("Session can not be null");
    	
    	T dao = null; 
        try
        {
        	DataAccessObject<?, ? extends Serializable> daoHibernate = getDAOHibernateClass(cls).newInstance();
        	daoHibernate.initialize(dbSession, dbSession.getCurrentEndUserId());
            dao = (T)daoHibernate; 
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Can not instantiate DAO: " + cls.getCanonicalName(), ex);
        }
        return dao;
        
    }
    
    private Class<? extends DataAccessObject<?, ? extends Serializable>> getDAOHibernateClass(Class<? extends IDataAccessObject<?, ? extends Serializable>> cls)
    {				
    	if (daoMap.containsKey(cls))
    		return daoMap.get(cls);

    	try
    	{
    		String businessClassName = ((Class<?>)(((ParameterizedType)cls.getGenericInterfaces()[0]).getActualTypeArguments()[0])).getSimpleName();
    		Class<? extends DataAccessObject<?, ? extends Serializable>> daoHibernateClass = (Class<? extends DataAccessObject<?, ? extends Serializable>>)Class.forName("framework.quickStart.dataAccessObjects.hibernate." + businessClassName);
    		registerDAO(cls, daoHibernateClass);
    		
    		return daoHibernateClass;
    	}
    	catch(Exception ex)
    	{
    		throw new RuntimeException("Can not find hibernate implementation for DAO class : " + cls.getCanonicalName());
    	}
    }
    
    public static void registerDAO(Class<? extends IDataAccessObject<?, ? extends Serializable>> key, Class<? extends DataAccessObject<?, ? extends Serializable>> value)
    {
    	daoMap.put(key, value);
    }

}
