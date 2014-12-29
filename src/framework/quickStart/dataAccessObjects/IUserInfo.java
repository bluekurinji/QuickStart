package framework.quickStart.dataAccessObjects;

public interface IUserInfo extends IDataAccessObject<framework.quickStart.businessObjects.UserInfo, java.util.UUID>{
	
	framework.quickStart.businessObjects.UserInfo authenticate(String loginName, String password);
}