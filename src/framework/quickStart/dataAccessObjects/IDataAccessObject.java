package framework.quickStart.dataAccessObjects;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface IDataAccessObject<T, ID extends Serializable> {
	
	void initialize(IDatabaseSession dbSession);
	
	T findById(ID id);
	
	List<T> findByIdList(List<ID> idList);
 
    List<T> findAll();
    
    List<T> findAll(Map<String, List<String>> conditionData);
 
    T save(T entity);
     
    void update(T entity);
 
    void delete(T entity);
    
    boolean isUnique(String columnName, Object columnValue, String primaryKeyColumnName, ID primaryKeyValue);
}
