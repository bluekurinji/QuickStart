package framework.quickStart.services.mediaType;

import java.util.List;

import framework.quickStart.businessObjects.IBusinessEntity;


public interface IListMediaType<BUSINESSOBJECT extends IBusinessEntity> {
	
	void initialize(List<BUSINESSOBJECT> businessObjectList);
	String toJson();
}
