package framework.quickStart.services.mediaType;
  
public class JsonConvertor 
{ 

	public static String toJson(Date date)
	{
		boolean modified = false;
		StringBuilder sb = new StringBuilder("{");

		{
			if (modified)
				sb.append(",");
			sb.append("\"day\":").append(date.day);
			modified = true;
		}

		{
			if (modified)
				sb.append(",");
			sb.append("\"month\":").append(date.month);
			modified = true;
		}

		{
			if (modified)
				sb.append(",");
			sb.append("\"year\":").append(date.year);
			modified = true;
		}

		{
			if (modified)
				sb.append(",");
			sb.append("\"hour\":").append(date.hour);
			modified = true;
		}

		{
			if (modified)
				sb.append(",");
			sb.append("\"min\":").append(date.min);
			modified = true;
		}

		sb.append("}");
		return sb.toString();
	}

	public static String toJson(UserInfo userinfo)
	{
		boolean modified = false;
		StringBuilder sb = new StringBuilder("{");

		{
			if (modified)
				sb.append(",");
			sb.append("\"displayName\":").append(userinfo.displayName);
			modified = true;
		}

		{
			if (modified)
				sb.append(",");
			sb.append("\"pictureUrl\":").append(userinfo.pictureUrl);
			modified = true;
		}

		sb.append("}");
		return sb.toString();
	}
}