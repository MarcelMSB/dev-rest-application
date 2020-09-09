package dev.marcel.application.model.produto;

import dev.marcel.application.model.common.entity.AbstractAuditEntity;
import dev.marcel.application.model.common.entity.AbstractEntityBuilder;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table(schema = "public", name = "produtos_consumiveis")
@SequenceGenerator(name = "produtos_consumiveis_seq", sequenceName = "produtos_consumiveis_seq", allocationSize = 1)
public class ProdutoConsumivel extends AbstractAuditEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produtos_consumiveis_seq")
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "i_produtos", nullable = false)
    private Produto produto;

    @NotNull
    @Column(name = "qtd_produto")
    private Long quantidade;

    @NotNull
    @DecimalMin(value = "0.01", message = "O valor do produto deve ser maior que 0,01")
    @Column(name = "vl_produto")
    private BigDecimal valor;

    protected ProdutoConsumivel() {
    }

    protected ProdutoConsumivel(final Produto produto) {
        this.produto = produto;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public static class Builder extends AbstractEntityBuilder<ProdutoConsumivel, Builder> {

        private Builder(ProdutoConsumivel credor) {
            super(credor);
        }

        public static Builder create(final Produto produto) {
            return new Builder(new ProdutoConsumivel(produto));
        }

        public static Builder from(ProdutoConsumivel credor) {
            return new Builder(credor);
        }

        public Builder quantidade(final Long quantidade) {
            entity.quantidade = quantidade;
            return this;
        }

        public Builder nome(final BigDecimal valor) {
            entity.valor = valor;
            return this;
        }
    }
}
