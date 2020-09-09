package dev.marcel.application.model.common.hibernate;

import java.time.LocalDate;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

import dev.marcel.application.model.common.entity.AbstractAuditEntity;

public final class AuditInsertEventListener implements PreInsertEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        Object obj = event.getEntity();
        if (obj instanceof AbstractAuditEntity) {
            AbstractAuditEntity entity = (AbstractAuditEntity) obj;
            if (entity.getDataCriacao() == null) {
                event.getState()[0] = LocalDate.now();
            }
        }
        return false;
    }
}
