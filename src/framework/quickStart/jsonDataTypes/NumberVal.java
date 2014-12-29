package framework.quickStart.jsonDataTypes;

public class NumberVal extends JsonValue {
	
	public NumberVal(byte value)
	{
		super(Byte.toString(value));
	}
	
	public NumberVal(int value)
	{
		super(Integer.toString(value));
	}
}
