package framework.quickStart.systemConfiguration;

import framework.quickStart.dataAccessObjects.IDataAccessObjectsFactory;
import framework.quickStart.dataAccessObjects.IDatabaseSession;

public class Settings {
	
	private static final Class<? extends IDataAccessObjectsFactory> DataAccessObjectFactoryType = framework.quickStart.dataAccessObjects.hibernate.DataAccessObjectFactory.class;
	private static final Class<? extends IDatabaseSession> DatabaseSessionType = framework.quickStart.dataAccessObjects.hibernate.DatabaseSession.class;
	
	private static final IDataAccessObjectsFactory dataAccessObjectsFactory;
	
	static 
	{	
        try {
        	dataAccessObjectsFactory = DataAccessObjectFactoryType.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create DAOFactory " + ex);
        }
	}
	
	private Settings()
	{
	}
		
	public static IDatabaseSession getDatabaseSession()
	{
        try {
        	return DatabaseSessionType.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create DBSession " + ex);
        }	
	}
	
	public static IDataAccessObjectsFactory getDataAccessObjectsFactory()
	{
		return dataAccessObjectsFactory;
	}
}
