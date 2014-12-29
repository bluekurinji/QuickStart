package framework.quickStart.util;

public class StringUtil {

	public static boolean isNullorEmpty(java.lang.String value)
	{
		return ((value == null) || (value.trim().length() == 0));
	}
}
