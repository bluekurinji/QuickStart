package framework.quickStart.services.mediaType;

import java.util.HashMap;
import java.util.Map;

public class MediaTypeFactory {
	
	private static Map<Class<?>, Class<? extends IReadOnlyMediaType<?>>> map = new HashMap<Class<?>, Class<? extends IReadOnlyMediaType<?>>>();
	
	public static <T extends IReadOnlyMediaType<?>> T getMediaType(Class<?> cls)
	{
		try
		{
			T resource = (T)getMediaTypeClass(cls).newInstance();
			return resource;
		}
		catch(Exception ex)
		{
			throw new RuntimeException("Can not instantiate MediaType for : " + cls.getCanonicalName(), ex);
		}
	}
	
    private static Class<? extends IReadOnlyMediaType<?>> getMediaTypeClass(Class<?> cls)
    {				
    	if (map.containsKey(cls))
    		return map.get(cls);

    	try
    	{
    		String businessClassName = cls.getSimpleName();
    		Class<? extends IReadOnlyMediaType<?>> mediaTypeClass = (Class<? extends IReadOnlyMediaType<?>>)Class.forName("framework.quickStart.services.mediaType." + businessClassName);
    		registerMediaType(cls, mediaTypeClass);
    		
    		return mediaTypeClass;
    	}
    	catch(Exception ex)
    	{
    		throw new RuntimeException("Can not find Media Type for business class : " + cls.getCanonicalName());
    	}
    }
	
    public static void registerMediaType(Class<?> key, Class<? extends IReadOnlyMediaType<?>> value)
    {
    	map.put(key, value);
    }
}
