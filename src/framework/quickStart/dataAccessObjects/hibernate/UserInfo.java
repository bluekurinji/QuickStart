package framework.quickStart.dataAccessObjects.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UserInfo extends
	DataAccessObject<framework.quickStart.businessObjects.UserInfo, java.util.UUID> implements
	framework.quickStart.dataAccessObjects.IUserInfo {

	@Override
	public framework.quickStart.businessObjects.UserInfo authenticate(String loginName, String password) {
		
		Criteria criteria = getSession().createCriteria(framework.quickStart.businessObjects.UserInfo.class);
		criteria.add(Restrictions.like("loginName", loginName));
		criteria.add(Restrictions.like("password", password));
		
		Object result = criteria.uniqueResult();
		if (result == null)
			return null;
		
		return (framework.quickStart.businessObjects.UserInfo) result;
	}
}