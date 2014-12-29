package framework.quickStart.services.mediaType;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import framework.quickStart.services.exceptions.InvalidMediaTypeException;

public class JsonUtil {
	
	public static <BUSINESSOBJECT> BUSINESSOBJECT convertToBusinessObject(String jsonObject, boolean idFieldRequired, Class<?> businessObjectClass, framework.quickStart.businessObjects.UserInfo user)
	{
		IMediaType<BUSINESSOBJECT> mediaType = MediaTypeFactory.getMediaType(businessObjectClass);
		
		try
		{
			return mediaType.fromJson(jsonObject, idFieldRequired);
		}
		catch(JSONException ex)
		{
			throw new InvalidMediaTypeException("Required fields are missing or invalid data", ex);
		}
	}

	public static <BUSINESSOBJECT> String convertToJson(BUSINESSOBJECT businessObject, Class<?> businessObjectClass, framework.quickStart.businessObjects.UserInfo currentUser)
	{
	    IMediaType<BUSINESSOBJECT> mediaType = MediaTypeFactory.getMediaType(businessObjectClass);
		mediaType.initialize(businessObject, currentUser);
		return mediaType.toJson();
	}
	
	public static <BUSINESSOBJECT> String convertToJson(List<BUSINESSOBJECT> resourceList, Class<?> businessObjectClass, framework.quickStart.businessObjects.UserInfo currentUser)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		if (resourceList.size() > 0)
		{
			IReadOnlyMediaType<BUSINESSOBJECT> mediaType = MediaTypeFactory.getMediaType(businessObjectClass);
			mediaType.initialize(resourceList.get(0), currentUser);
			sb.append(mediaType.toJson());
		}
		
		for(int i=1; i<resourceList.size(); i++)
		{
			sb.append(",");
			
			IReadOnlyMediaType<BUSINESSOBJECT> mediaType = MediaTypeFactory.getMediaType(businessObjectClass);
			mediaType.initialize(resourceList.get(i), currentUser);
			sb.append(mediaType.toJson());
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	public static String convertToJson(Map<String, String> data)
	{
		String[] keys = data.keySet().toArray(new String[data.size()]);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		if (data.size() > 0)
			sb.append("{\"").append(keys[0]).append("\":").append(data.get(keys[0])).append("}");
		
		for(int i=1; i < data.size(); i++)
			sb.append(",{\"").append(keys[i]).append("\":").append(data.get(keys[i])).append("}");
		
		sb.append("}");
		return sb.toString();
	}
	
	public static String convertToJson(String[] values)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		if (values.length > 0)
			sb.append("\"").append(values[0]).append("\"");
		
		for(int i=1; i<values.length; i++)
			sb.append(",\"").append(values[i]).append("\"");
		
		sb.append("]");
		
		return sb.toString();		
	}
}