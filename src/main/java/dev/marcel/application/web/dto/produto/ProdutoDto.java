package dev.marcel.application.web.dto.produto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dev.marcel.application.web.dto.common.AbstractDtoBuilder;
import dev.marcel.application.web.dto.common.AbstractRepresentationBuilder;
import dev.marcel.application.model.produto.Produto;

public class ProdutoDto {

    private Long id;
    private String nome;
    private Long unidadeMedida;
    private LocalDate dataCriacao;
    private LocalDateTime dataHoraModificacao;

    protected ProdutoDto() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Long getUnidadeMedida() {
        return unidadeMedida;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataHoraModificacao() {
        return dataHoraModificacao;
    }

    public static class Builder extends AbstractDtoBuilder<ProdutoDto> {

        private Builder(final ProdutoDto dto) {
            super(dto);
        }

        public static Builder create() {
            return new Builder(new ProdutoDto());
        }

        public Builder id(final Long id) {
            dto.id = id;
            return this;
        }

        public Builder nome(String nome) {
            dto.nome = nome;
            return this;
        }

        public Builder unidadeMedida(Long unidadeMedida) {
            dto.unidadeMedida = unidadeMedida;
            return this;
        }

        public Builder dataCriacao(LocalDate dataCriacao) {
            dto.dataCriacao = dataCriacao;
            return this;
        }

        public Builder dataHoraModificacao(LocalDateTime dataHoraModificacao) {
            dto.dataHoraModificacao = dataHoraModificacao;
            return this;
        }
    }

    public static class RepresentationBuilder extends AbstractRepresentationBuilder<Produto, ProdutoDto, Produto.Builder> {

        @Override
        public Produto fromRepresentation(ProdutoDto dto, Produto.Builder builder) {
            return builder.nome(dto.getNome()).unidadeMedida(dto.getUnidadeMedida()).build();
        }

        @Override
        public ProdutoDto toRepresentation(Produto entity) {
            return ProdutoDto.Builder.create()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .unidadeMedida(entity.getUnidadeMedida())
                    .dataCriacao(entity.getDataCriacao())
                    .dataHoraModificacao(entity.getDataHoraModificacao())
                    .build();
        }
    }
}
