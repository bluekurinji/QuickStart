package framework.quickStart.services.security;

public class CurrentUser extends framework.quickStart.businessObjects.UserInfo implements java.security.Principal {

	framework.quickStart.businessObjects.UserInfo userInfo;
	
	public CurrentUser(framework.quickStart.businessObjects.UserInfo userInfo)
	{
		this.setFirstName(userInfo.getFirstName());
		this.setLastName(userInfo.getLastName());
		this.setUserId(userInfo.getUserId());
		this.setEnabled(userInfo.isEnabled());
		this.setLoginName(userInfo.getLoginName());
		
		this.userInfo = userInfo;
	}
	
	public framework.quickStart.businessObjects.UserInfo getUser()
	{
		return this.userInfo;
	}
	
	@Override
	public String getName() {
		return this.getDisplayName();
	}

}
