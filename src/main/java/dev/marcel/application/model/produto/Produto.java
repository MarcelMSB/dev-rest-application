package dev.marcel.application.model.produto;

import javax.inject.Inject;

import dev.marcel.application.model.common.entity.AbstractAuditEntity;
import dev.marcel.application.model.common.entity.AbstractEntityBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.expr.BooleanExpression;

import dev.marcel.application.common.util.CDIUtil;

@Entity
@Table(schema = "public", name = "produtos")
@SequenceGenerator(name = "produtos_seq", sequenceName = "produtos_seq", allocationSize = 1)
public class Produto extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produtos_seq")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 255, message = "Informe um nome válido")
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Max(999)
    @Column(name = "i_unidades_medidas")
    private Long unidadeMedida;

    protected Produto() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Long getUnidadeMedida() {
        return unidadeMedida;
    }

    @QueryDelegate(Produto.class)
    public static BooleanExpression eqBusinessKey(final QProduto q, final Produto produto) {
        BooleanExpression query = QProduto.produto.nome.eq(produto.getNome());
        return produto.getId() != null ? query.and(q.id.ne(produto.getId())) : query;
    }

    public static class Builder extends AbstractEntityBuilder<Produto, Builder> {

        @Inject
        private ProdutoRepository repository;

        private Builder(Produto produto) {
            super(produto);
        }

        public static Builder create() {
            return CDIUtil.injectFields(new Builder(new Produto()));
        }

        public static Builder from(final Produto produto) {
            return CDIUtil.injectFields(new Builder(produto));
        }

        @Override
        protected void validate() {
            super.validate();
            if (repository.existsByBusinessKey(entity)) {
                throw new ValidationException("Existe um produto com o nome: " + entity.getNome());
            }
        }

        public Builder nome(final String nome) {
            entity.nome = nome;
            return this;
        }

        public Builder unidadeMedida(final Long unidadeMedida) {
            entity.unidadeMedida = unidadeMedida;
            return this;
        }
    }
}
