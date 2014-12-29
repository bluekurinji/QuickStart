package framework.quickStart.dataAccessObjects.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import framework.quickStart.dataAccessObjects.IDatabaseSession;

public class DatabaseSession implements IDatabaseSession {

	Session currentSession = null;
	java.util.UUID currentEndUserId = null;

	public DatabaseSession() {
		
	}
	
	public void begin(java.util.UUID currentEndUserId) {		
		this.currentEndUserId = currentEndUserId;
		currentSession = SessionFactory.getSessionFactory().openSession();
		currentSession.beginTransaction();
	}
	
	public java.util.UUID getCurrentEndUserId()
	{
		return this.currentEndUserId;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void commit() {
		currentSession.getTransaction().commit();
	}

	public void rollback() {
		try {
			currentSession.getTransaction().rollback();
		} catch (HibernateException e) {
			throw new RuntimeException("Cannot rollback", e);
		}

		end();
	}

	public void end() {
		try {
			if (currentSession != null)
				currentSession.close();
		} catch (HibernateException e) {
			throw new RuntimeException("Cannot close", e);
		}
		
		currentSession = null;
	}
}
