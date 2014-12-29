package framework.quickStart.util;

import framework.quickStart.services.exceptions.InternalFailureException;
import framework.quickStart.services.mediaType.Date;

public class Properties {
	
	private static java.util.Properties properties;
	private static String serviceRootPath;
	private static String uiRootPath;
	private static String cookieDomain;
	private static String cookiePath;
	private static Boolean isCookieSecure;
	private static Integer systemVersion;
	private static Integer defaultSearchDistance;
	private static String categoryDefinitionPath;
	private static String categoryDefinitionPackageName;
	private static String uiTemplatePath;
	private static String uploadFilePath;
	private static Integer uploadImageSize;
	
	static
	{
		properties = new java.util.Properties();
		try {
			properties.load(Date.class.getClassLoader().getResourceAsStream("makkal.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalFailureException(ex);
		}
	}
	
	public static String getServiceRootPath()
	{
		if (serviceRootPath != null)
			return serviceRootPath;
		
		serviceRootPath = properties.getProperty("ServiceRootPath");
		return serviceRootPath;
	}
	
	public static String getUIRootPath()
	{
		if (uiRootPath != null)
			return uiRootPath;
		
		uiRootPath = properties.getProperty("UIRootPath");
		return uiRootPath;
	}
	
	public static String getCookieDomain()
	{
		if (cookieDomain != null)
			return cookieDomain;
		
		cookieDomain = properties.getProperty("CookieDomain");
		return cookieDomain;
	}	
	
	public static String getCookiePath()
	{
		if (cookiePath != null)
			return cookiePath;
		
		cookiePath = properties.getProperty("CookiePath");
		return cookiePath;
	}
	
	public static boolean isCookieSecure()
	{
		if (isCookieSecure != null)
			return isCookieSecure.booleanValue();
		
		isCookieSecure = Boolean.getBoolean(properties.getProperty("IsCookieSecure"));
		return isCookieSecure.booleanValue();
	}	
	
	public static int getSystemVersion()
	{
		if (systemVersion != null)
			return systemVersion.intValue();
		
		systemVersion = Integer.parseInt(properties.getProperty("SystemVersion"));
		return systemVersion.intValue();
	}
	
	public static int getDefaultSearchDistance()
	{
		if (defaultSearchDistance != null)
			return defaultSearchDistance.intValue();
		
		defaultSearchDistance = Integer.parseInt(properties.getProperty("DefaultSearchDistance"));
		return defaultSearchDistance.intValue();
	}
	
	public static String getCategoryDefinitionPath()
	{
		if (categoryDefinitionPath != null)
			return categoryDefinitionPath;
		
		categoryDefinitionPath = properties.getProperty("CategoryDefinitionPath");
		return categoryDefinitionPath;
	}
	
	public static String getCategoryDefinitionPackageName()
	{
		if (categoryDefinitionPackageName != null)
			return categoryDefinitionPackageName;
		
		categoryDefinitionPackageName = properties.getProperty("CategoryDefinitionPackageName");
		return categoryDefinitionPackageName;
	}
	
	public static String getUITemplatePath()
	{
		if (uiTemplatePath != null)
			return uiTemplatePath;
		
		uiTemplatePath = properties.getProperty("UITemplatePath");
		return uiTemplatePath;
	}
	
	public static String getUploadFilePath()
	{
		if (uploadFilePath != null)
			return uploadFilePath;
		
		uploadFilePath = properties.getProperty("uploadFilePath");
		return uploadFilePath;
	}
	
	public static int getUploadImageSize()
	{
		if (uploadImageSize != null)
			return uploadImageSize.intValue();
		
		uploadImageSize = Integer.parseInt(properties.getProperty("uploadImageSize"));
		return uploadImageSize.intValue();
	}
}