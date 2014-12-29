package framework.quickStart.resourceAccessors;

import framework.quickStart.dataAccessObjects.IDataAccessObjectsFactory;
import framework.quickStart.dataAccessObjects.IDatabaseSession;
import framework.quickStart.dataAccessObjects.IUserInfo;
import framework.quickStart.systemConfiguration.Settings;

public class UserInfo extends ResourceAccessor<java.util.UUID, framework.quickStart.businessObjects.UserInfo, IUserInfo>{
	
	public framework.quickStart.businessObjects.UserInfo authenticate(String loginName, String password) {
		IDatabaseSession dbSession = Settings.getDatabaseSession();
		dbSession.begin(getCurrentEndUserId());
		
		IDataAccessObjectsFactory daoFactory = Settings.getDataAccessObjectsFactory();
		
		IUserInfo dao = daoFactory.getDAO(IUserInfo.class, dbSession);
		framework.quickStart.businessObjects.UserInfo userInfo = dao.authenticate(loginName, password);

		dbSession.commit();
		dbSession.end();
		
		return userInfo;
	}
}