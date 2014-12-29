package framework.quickStart.resourceAccessors;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

import framework.quickStart.businessObjects.IBusinessEntity;
import framework.quickStart.dataAccessObjects.IDataAccessObject;
import framework.quickStart.dataAccessObjects.IDataAccessObjectsFactory;
import framework.quickStart.dataAccessObjects.IDatabaseSession;
import framework.quickStart.systemConfiguration.Settings;

public abstract class ResourceAccessor<ID extends Serializable, BUSINESSOBJECT extends IBusinessEntity, DATAACCESSOBJECT extends IDataAccessObject<BUSINESSOBJECT, ID>> {
	
	protected Class<DATAACCESSOBJECT> daoClass = (Class<DATAACCESSOBJECT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	protected Class<BUSINESSOBJECT> businessClass = (Class<BUSINESSOBJECT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	
	protected IDataAccessObjectsFactory daoFactory = Settings.getDataAccessObjectsFactory();
	protected framework.quickStart.businessObjects.UserInfo user;
			
	public ResourceAccessor()
	{
	}
	
	public framework.quickStart.businessObjects.UserInfo getCurrentUser() {
		return user;
	}

	public void setCurrentUser(framework.quickStart.businessObjects.UserInfo user) {
		this.user = user;
	}

	public java.util.UUID getCurrentEndUserId()
	{
		if (getCurrentUser() == null)
			return null;
		
		return getCurrentUser().getUserId();
	}
	
	public void authorizeAccess(ID id, IDatabaseSession dbSession) throws IllegalAccessException {
	
		// Currently used by item resource accessors
	}
	
	public BUSINESSOBJECT getResource(ID id)
	{	
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
		
		BUSINESSOBJECT businessObject = getResource(id, dbSession);

		dbSession.commit();
		dbSession.end();
		
		return businessObject;
	}
	
	public BUSINESSOBJECT getResource(ID id, IDatabaseSession dbSession)
	{			
		IDataAccessObject<BUSINESSOBJECT, ID> dao = daoFactory.getDAO(daoClass, dbSession);
		BUSINESSOBJECT businessObject = dao.findById(id);
		return businessObject;
	}
	
	public boolean exists(ID id)
	{
		BUSINESSOBJECT businessObject = getResource(id);
		return (businessObject != null);
	}
	
	public boolean isUnique(String columnName, Object columnValue, String idPropertyName, ID id)
	{
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
				
		IDataAccessObject<BUSINESSOBJECT, ID> dao = daoFactory.getDAO(daoClass, dbSession);	
		boolean value = dao.isUnique(columnName, columnValue, idPropertyName, id);
	
		dbSession.end();
		return value;
	}
	
	public boolean deleteResource(ID id)
	{		
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
				
		boolean result = false;
		try
		{
			result = deleteResource(id, dbSession);
			dbSession.commit();
		}
		catch(Exception ex)
		{
			dbSession.rollback();
			throw new ResourceDeleteFailedException(ex);
		}
		finally
		{
			dbSession.end();
		}
		
		return result;
	}
	
	public boolean deleteResource(ID id, IDatabaseSession dbSession) throws IllegalAccessException
	{		
		authorizeAccess(id, dbSession);
		
		BUSINESSOBJECT businessObject = getResource(id, dbSession);
		
		if (businessObject == null)
			return false;
				
		IDataAccessObject<BUSINESSOBJECT, ID> dao = daoFactory.getDAO(daoClass, dbSession);
		dao.delete(businessObject);
		
		return true;
	}
		
	public List<BUSINESSOBJECT> getAllResource(Map<String, List<String>> conditions)		
	{		
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
		
		List<BUSINESSOBJECT> resourceList = getAllResource(conditions, dbSession);

		dbSession.commit();
		dbSession.end();
		
		return resourceList;
	}
	
	public List<BUSINESSOBJECT> getAllResource(Map<String, List<String>> conditions, IDatabaseSession dbSession)		
	{			
		IDataAccessObject<BUSINESSOBJECT, ID> dao = daoFactory.getDAO(daoClass, dbSession);
		List<BUSINESSOBJECT> resourceList = dao.findAll(conditions);

		return resourceList;
	}
	
	public BUSINESSOBJECT createResource(BUSINESSOBJECT resource)
	{		
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
		
		try
		{			
			createResource(resource, dbSession);
			dbSession.commit();
		}
		catch(ResourceCreateFailedException ex)
		{
			dbSession.rollback();
			throw ex;
		}
		finally
		{
			dbSession.end();
		}		
		
		return resource;	
	}
	
	public BUSINESSOBJECT createResource(BUSINESSOBJECT resource, IDatabaseSession dbSession)
	{
		if (resource == null)
			throw new IllegalArgumentException("resource");
		
		if (framework.quickStart.businessObjects.IAutoGenerateIdEntity.class.isAssignableFrom(resource.getClass()))
			((framework.quickStart.businessObjects.IAutoGenerateIdEntity)resource).setGeneratedId();
		
		IDataAccessObject<BUSINESSOBJECT, ID> dao = daoFactory.getDAO(daoClass, dbSession);
		
		try
		{			
			resource = dao.save(resource);
		}
		catch(Exception ex)
		{
			throw new ResourceCreateFailedException(ex);
		}		
		
		return resource;	
	}
	
	public void updateResource(BUSINESSOBJECT resource)
	{		
		if (resource == null)
			throw new IllegalArgumentException("resource");
		
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
			
		try
		{
			updateResource(resource, dbSession);
			dbSession.commit();
		}
		catch(Exception ex)
		{
			dbSession.rollback();
			throw new ResourceUpdateFailedException(ex);
		}
		finally
		{
			dbSession.end();
		}		
	}
	
	public void updateResource(BUSINESSOBJECT resource, IDatabaseSession dbSession) throws IllegalAccessException 
	{				
		authorizeAccess((ID)resource.getId(), dbSession);
		IDataAccessObject<BUSINESSOBJECT, ID> dao = daoFactory.getDAO(daoClass, dbSession);
		dao.update(resource);		
	}


	public void updateResource(ID id, HashMap<String, String> resourceData)
	{
		if (resourceData == null)
			throw new IllegalArgumentException("resourceData");
				
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
				
		IDataAccessObject<BUSINESSOBJECT, ID> dao = daoFactory.getDAO(daoClass, dbSession);
		BUSINESSOBJECT businessObject = dao.findById(id);
		
		if (businessObject == null)
			throw new ResourceUpdateFailedException("Can not update. The resource does not exist.");
		
		BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new EnumAwareConvertUtilsBean());
		
		try {
			authorizeAccess(id, dbSession);
			beanUtilsBean.populate(businessObject, resourceData);
			dbSession.commit();
		} catch (IllegalAccessException e) {
			dbSession.rollback();
			throw new ResourceUpdateFailedException(e);
		} catch (InvocationTargetException e) {
			dbSession.rollback();
			throw new ResourceUpdateFailedException(e);
		}
		catch(Exception ex)
		{
			dbSession.rollback();
			throw new ResourceUpdateFailedException(ex);
		}
		finally
		{
			dbSession.end();
		}
	}

	class EnumAwareConvertUtilsBean extends ConvertUtilsBean {
	    private final EnumConverter enumConverter = new EnumConverter();

	    public Converter lookup(Class clazz) {
	        final Converter converter = super.lookup(clazz);
	        // no specific converter for this class, so it's neither a String, (which has a default converter),
	        // nor any known object that has a custom converter for it. It might be an enum !
	        if (converter == null && clazz.isEnum()) {
	            return enumConverter;
	        } else {
	            return converter;
	        }
	    }

	    private class EnumConverter implements Converter {
	        public Object convert(Class type, Object value) {
	            return Enum.valueOf(type, (String) value);
	        }
	    }
	}
}
