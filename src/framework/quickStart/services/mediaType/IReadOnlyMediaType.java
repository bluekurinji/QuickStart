package framework.quickStart.services.mediaType;

public interface IReadOnlyMediaType<OBJECT>{

	void initialize(OBJECT businessObject, framework.quickStart.businessObjects.UserInfo user);
	String toJson();
}
