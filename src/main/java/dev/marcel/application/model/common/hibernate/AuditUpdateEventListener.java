package dev.marcel.application.model.common.hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import dev.marcel.application.model.common.entity.AbstractAuditEntity;

public final class AuditUpdateEventListener implements PreUpdateEventListener {

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        Object obj = event.getEntity();
        if (obj instanceof AbstractAuditEntity) {
            AbstractAuditEntity entity = (AbstractAuditEntity) obj;
            if (entity.getDataCriacao() == null) {
                event.getState()[0] = LocalDate.now();
            }
            event.getState()[1] = LocalDateTime.now();
        }
        return false;
    }
}
