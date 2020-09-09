package dev.marcel.application.model.common.repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.JPQLTemplates;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.jpa.impl.JPAProvider;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.PathBuilderFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import org.hibernate.Session;
import dev.marcel.application.model.common.entity.IEntityId;

public abstract class AbstractRepository<T extends IEntityId> {

    @Inject
    protected EntityManager em;

    private final PathBuilderFactory pathBuilderFactory = new PathBuilderFactory();

    protected Class<T> type;
    protected PathBuilder<T> entityPath;
    private JPQLTemplates jpqlTemplate;

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void init() {
        this.jpqlTemplate = JPAProvider.getTemplates(em);
        this.type = getMappedSuperclass(getClass());
        this.entityPath = getPathBuilderFactory().create(this.type);
    }

    public JPQLQuery from(final EntityPath<?>... paths) {
        return new HibernateQuery(em.unwrap(Session.class), jpqlTemplate).from(paths);
    }

    protected void throwEntityNotFoundException(final Long id) throws EntityNotFoundException {
        throw new ValidationException("Entidade " + type.getSimpleName() + " com o ID: " + id + " não encontrada");
    }

    public List<T> findAll(final Predicate... predicates) {
        return from(entityPath).where(predicates).list(entityPath);
    }

    public T findBy(Long id) {
        return em.find(type, id);
    }

    public T findByOrElseThrow(Long id) throws EntityNotFoundException {
        final T entity = findBy(id);
        if (entity == null) {
            throwEntityNotFoundException(id);
        }
        return entity;
    }
    
    public T getReferenceBy(final Long id) {
        return em.getReference(type, id);
    }
    
    protected EntityManager getEntityManager() {
        return em;
    }

    protected PathBuilder<T> getEntityPath() {
        return entityPath;
    }

    protected Class<T> getEntityType() {
        return type;
    }

    private Class getMappedSuperclass(final Class<?> clazz) {
        return getMappedSuperclass(clazz, 0);
    }

    private Class getMappedSuperclass(final Class<?> clazz, int arg) {
        if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType genericSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
            return (Class) genericSuperclass.getActualTypeArguments()[arg];
        }
        return null;
    }

    private PathBuilderFactory getPathBuilderFactory() {
        return pathBuilderFactory;
    }
}
