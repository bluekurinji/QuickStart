package framework.quickStart.services.mediaType;

import org.json.JSONObject;

public interface IMediaType<OBJECT> extends IReadOnlyMediaType<OBJECT> {
	
	OBJECT fromJson(String jsonObject, boolean idFieldRequired);
	OBJECT fromJson(JSONObject jsonObject, boolean idFieldRequired);
}
