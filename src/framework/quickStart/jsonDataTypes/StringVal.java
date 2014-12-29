package framework.quickStart.jsonDataTypes;

public class StringVal extends JsonValue {
	
	public StringVal(String value)
	{
		super(value);
	}
	
	public StringVal(java.math.BigDecimal value)
	{
		super(value != null ? value.toString() : null);
	}
	
	@Override
	public String toString() {
		
		if (isNull())
			return "null";
		
		String value = super.toString();
		
		StringBuilder sb = new StringBuilder(value.length() + 2);
		return sb.append("\"").append(value).append("\"").toString();
	}

}
