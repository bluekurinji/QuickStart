package framework.quickStart.dataAccessObjects;

public interface IDatabaseSession {

	void begin(java.util.UUID currentEndUserId);
		
	void commit();
	
	void rollback();
	
	void end();
	
	java.util.UUID getCurrentEndUserId();
}