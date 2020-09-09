package dev.marcel.application.model.common.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractService<T> {

    @PersistenceContext
    protected EntityManager em;
    
    public T persist(T t) {
        return em.merge(t);
    }
    
    public T merge(T t) {
        return em.merge(t);
    }

    public void remove(T t) {
        em.remove(t);
    }
}
