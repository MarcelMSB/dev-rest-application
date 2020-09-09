package dev.marcel.application.model.common.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAuditEntity extends AbstractLongIdEntity {

    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "dth_modificacao")
    private LocalDateTime dataHoraModificacao;

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataHoraModificacao() {
        return dataHoraModificacao;
    }

    public void setDataHoraModificacao(LocalDateTime dataHoraModificacao) {
        this.dataHoraModificacao = dataHoraModificacao;
    }
}
