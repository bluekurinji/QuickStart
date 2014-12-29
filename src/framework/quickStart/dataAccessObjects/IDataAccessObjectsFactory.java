package framework.quickStart.dataAccessObjects;

import java.io.Serializable;

public interface IDataAccessObjectsFactory {
    
    <S, ID extends Serializable, T extends IDataAccessObject<S, ID>> T getDAO(Class<T> cls, IDatabaseSession dbSession);
}

