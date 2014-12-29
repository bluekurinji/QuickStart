package framework.quickStart.jsonDataTypes;

public abstract class JsonValue {

	private String value;
	
	protected JsonValue(String value)
	{
		this.value = value;
	}
	
	public boolean isNull()
	{
		return (this.value == null);
	}
	
	public String getStringValue()
	{
		return value;
	}
	
	public String toString()
	{
		if (isNull())
			return "null";
		
		return value;
	}
}
