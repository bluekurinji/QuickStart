package framework.quickStart.dataAccessObjects.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import framework.quickStart.dataAccessObjects.IDataAccessObject;
import framework.quickStart.dataAccessObjects.IDatabaseSession;

public abstract class DataAccessObject<T, ID extends Serializable> implements
		IDataAccessObject<T, ID> {

	private Class<T> persistentClass;
	private IDatabaseSession dbSession;
	private Session session;
	private java.util.UUID currentEndUserId;
	
    protected DataAccessObject() { 	
  
		try {
			this.persistentClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void initialize(IDatabaseSession dbSession)
    {
    	this.dbSession = dbSession;
    	this.session = ((DatabaseSession)dbSession).getCurrentSession();
    	this.currentEndUserId = ((DatabaseSession)dbSession).currentEndUserId;
    }

    void initialize(IDatabaseSession dbSession, java.util.UUID currentEndUserId) {

    	this.dbSession = dbSession;
    	this.session = ((DatabaseSession)dbSession).getCurrentSession();
		this.currentEndUserId = currentEndUserId;
	}
    
    protected IDatabaseSession getDBSession()
    {
    	return dbSession;
    }

	protected Session getSession() {
		if (session == null)
			throw new IllegalArgumentException(
					"Session has not been set on DAO before usage");
		return session;
	}
	
	protected java.util.UUID getCurrentUserId()
	{
		return this.currentEndUserId;
	}
	
	public void SetPersistentClass(Class<T> persistentClass)
	{
		this.persistentClass = persistentClass;
	}

	protected Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T findById(ID id) {

		Criteria criteria = getById(id);
		Object data = criteria.uniqueResult();
		return (data == null) ? null : (T)data;
	}
	
	public List<T> findByIdList(List<ID> idList)
	{
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.in(getIdPropertyName(), idList));
		
		List<T> list = criteria.list();
		return list;
	}

	public List<T> findAll() {
		return findByCriteria();
	}
	
	public List<T> findAll(Map<String, List<String>> conditionData) {
		
		if (conditionData == null) 
			throw new IllegalArgumentException("conditionData can not be null");
		
		if (conditionData.size() == 0)
			return findAll();
		
		Criteria criteria = buildFindCriteria(conditionData);
				
		List<T> list = criteria.list();
		return list;
	}
	
	public Criteria buildFindCriteria(Map<String, List<String>> conditionData)
	{
		throw new RuntimeException("Yet to be implemented");
	}

	public T save(T entity) {
		getSession().save(entity);
		return entity;
	}
	
	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	public boolean isUnique(String columnName, Object columnValue, String primaryKeyColumnName, ID primaryKeyValue)
	{
		Criteria criteria = this.getSession().createCriteria(persistentClass);
		
		if (columnValue.getClass() == String.class)
			criteria.add(Restrictions.like(columnName, columnValue));
		else
			criteria.add(Restrictions.eq(columnName, columnValue));

		if (primaryKeyValue != null)
			criteria.add(Restrictions.ne(primaryKeyColumnName, primaryKeyValue));
		
		List<?> list = criteria.list();
		return (list.size() == 0);
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = this.getCriteria();
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	protected Criteria getCriteria()
	{
		return getSession().createCriteria(this.getPersistentClass());
	}
	
	private Criteria getById(ID id)
	{
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq(getIdPropertyName(), id));		
		return criteria;
	}
	
	protected String getIdPropertyName() {
		String idProperty = SessionFactory.getSessionFactory()
		                                              .getClassMetadata(persistentClass)
		                                              .getIdentifierPropertyName();
		return idProperty;
	}
}