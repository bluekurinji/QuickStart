package framework.quickStart.businessObjects;

import java.util.Date;

import javax.persistence.*;

@Entity
public class UserInfo implements IBusinessEntity, IAutoGenerateIdEntity {
	
	public static final String ID_FIELD_NAME = "userId";
	
	private java.util.UUID userId;
	private String firstName;
	private String lastName;
	private String loginName;
	private String password;
	private boolean enabled;
	
	private String postalCode1;	
	private Double latitude1;
	private Double longitude1;
	
	private String postalCode2;	
	private Double latitude2;
	private Double longitude2;
	
	private String postalCode3;
	private Double latitude3;
	private Double longitude3;
	
	private Date createdOn;
	private Date lastMofiedOn;
	
	public UserInfo()
	{
	}
	
	public UserInfo(java.util.UUID userId)
	{
		this.userId = userId;
	}

	@Id
	@Column( columnDefinition = "BINARY(16)", length = 16 )
	public java.util.UUID getUserId() {
		return userId;
	}

	protected void setUserId(java.util.UUID userId) {
		this.userId = userId;
	}

	@Column(nullable=false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(nullable=false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(nullable=false)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable=false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(nullable=false)
	public String getPostalCode1() {
		return postalCode1;
	}

	public void setPostalCode1(String postalCode1) {
		this.postalCode1 = postalCode1;
	}

	@Column(nullable=false)
	public Double getLatitude1() {
		return latitude1;
	}

	public void setLatitude1(Double latitude1) {
		this.latitude1 = latitude1;
	}

	@Column(nullable=false)
	public Double getLongitude1() {
		return longitude1;
	}

	public void setLongitude1(Double longitude1) {
		this.longitude1 = longitude1;
	}

	public String getPostalCode2() {
		return postalCode2;
	}

	public void setPostalCode2(String postalCode2) {
		this.postalCode2 = postalCode2;
	}

	public Double getLatitude2() {
		return latitude2;
	}

	public void setLatitude2(Double latitude2) {
		this.latitude2 = latitude2;
	}

	public Double getLongitude2() {
		return longitude2;
	}

	public void setLongitude2(Double longitude2) {
		this.longitude2 = longitude2;
	}

	public String getPostalCode3() {
		return postalCode3;
	}

	public void setPostalCode3(String postalCode3) {
		this.postalCode3 = postalCode3;
	}

	public Double getLatitude3() {
		return latitude3;
	}

	public void setLatitude3(Double latitude3) {
		this.latitude3 = latitude3;
	}

	public Double getLongitude3() {
		return longitude3;
	}

	public void setLongitude3(Double longitude3) {
		this.longitude3 = longitude3;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastMofiedOn() {
		return lastMofiedOn;
	}

	public void setLastMofiedOn(Date lastMofiedOn) {
		this.lastMofiedOn = lastMofiedOn;
	}

	@Transient
	public String getDisplayName()
	{
		return firstName + " " + lastName;
	}

	@Override
	public void setGeneratedId() {
		this.setUserId(UUIDUtil.getRandomUUID());
	}
	
	@Transient
	@Override
	public java.util.UUID getId()
	{
		return this.getUserId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserInfo))
			return false;
		UserInfo other = (UserInfo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}