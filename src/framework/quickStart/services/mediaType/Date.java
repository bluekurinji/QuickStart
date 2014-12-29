package framework.quickStart.services.mediaType;

import java.util.Calendar;

import org.json.JSONObject;

import framework.quickStart.jsonDataTypes.NumberVal;


public class Date implements IMediaType<java.util.Date> {
	
	private static final String DAY = "day";
	private static final String MONTH = "month";
	private static final String YEAR = "year";
	private static final String HOUR = "hour";
	private static final String MIN = "min";

	public NumberVal day;
	public NumberVal month;
	public NumberVal year;
	public NumberVal hour;
	public NumberVal min;
	
	framework.quickStart.businessObjects.UserInfo currentUser;
	
	public Date()
	{
	}
	
	public Date(java.util.Date date)
	{
		initialize(date, null);
	}
	
	@Override
	public void initialize(java.util.Date date, framework.quickStart.businessObjects.UserInfo currentUser) {
		
		this.currentUser = currentUser;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		this.day = new NumberVal(calendar.get(Calendar.DAY_OF_MONTH));
		this.month = new NumberVal(calendar.get(Calendar.MONTH));
		this.year = new NumberVal(calendar.get(Calendar.YEAR));
		this.hour = new NumberVal(calendar.get(Calendar.HOUR_OF_DAY));
		this.min = new NumberVal(calendar.get(Calendar.MINUTE));
	}

	@Override
	public String toJson() {
		return JsonConvertor.toJson(this);
	}

	@Override
	public java.util.Date fromJson(String jsonObjectAsString, boolean idFieldRequired) {
		JSONObject jsonObject = new JSONObject(jsonObjectAsString);
		return fromJson(jsonObject, idFieldRequired);
	}

	@Override
	public java.util.Date fromJson(JSONObject jsonObject, boolean idFieldRequired) {

		int day = jsonObject.getInt(DAY);
		int month = jsonObject.getInt(MONTH);
		int year = jsonObject.getInt(YEAR);
		int hour = jsonObject.getInt(HOUR); 
		int min = jsonObject.getInt(MIN);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, min, 0);		
		return calendar.getTime();
	}
}
