package framework.quickStart.services.mediaType;

import org.json.JSONObject;

import framework.quickStart.jsonDataTypes.StringVal;
import framework.quickStart.services.exceptions.NotSupportedException;

public class UserInfo implements IMediaType<framework.quickStart.businessObjects.UserInfo>{

	public StringVal displayName;
	public StringVal pictureUrl = new StringVal("TBD");
	
	@Override
	public void initialize(framework.quickStart.businessObjects.UserInfo userInfo, framework.quickStart.businessObjects.UserInfo currentUser) {
		
		this.displayName = new StringVal(userInfo.getDisplayName());
	}

	@Override
	public String toJson() {
		return JsonConvertor.toJson(this);
	}
	
	@Override
	public framework.quickStart.businessObjects.UserInfo fromJson(JSONObject jsonObject, boolean idFieldRequired) {
		throw new NotSupportedException("UserDisplayInfo is for reading purpose only");
	}

	@Override
	public framework.quickStart.businessObjects.UserInfo fromJson(String jsonObjectAsString, boolean idFieldRequired) {
		throw new NotSupportedException("UserDisplayInfo is for reading purpose only");
	}
}
