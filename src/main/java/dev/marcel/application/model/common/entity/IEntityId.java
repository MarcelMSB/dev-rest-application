package dev.marcel.application.model.common.entity;

import java.io.Serializable;

public interface IEntityId<PK> extends Serializable {

    public PK getId();
}
