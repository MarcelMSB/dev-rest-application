package dev.marcel.application.model.common.entity;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
@SuppressWarnings("serial")
public class EntityProducer {

    @Produces
    @PersistenceContext(unitName = "appPU")
    private EntityManager em;
}
