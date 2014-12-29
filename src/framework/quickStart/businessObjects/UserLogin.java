package framework.quickStart.businessObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class UserLogin implements IBusinessEntity {

	private java.util.UUID userId;
	private String loginName;
	private String sessionId;
	private java.util.Date authTokenCreationTime;
	
	public UserLogin()
	{
	}

	@Column( columnDefinition = "BINARY(16)", length = 16 )
	public java.util.UUID getUserId() {
		return userId;
	}

	public void setUserId(java.util.UUID userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Id
	@Column(nullable=true)
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(nullable=true)
	public java.util.Date getAuthTokenCreationTime() {
		return authTokenCreationTime;
	}

	public void setAuthTokenCreationTime(java.util.Date authTokenCreationTime) {
		this.authTokenCreationTime = authTokenCreationTime;
	}
	
	@Transient
	@Override
	public java.util.UUID getId()
	{
		return this.userId;
	}
}
