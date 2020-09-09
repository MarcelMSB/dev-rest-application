package dev.marcel.application.model.ibge.feign.dto;

public class EstadoDto {

    private Long id;
    private String nome;
    private String sigla;
    private RegiaoDto regiao;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public RegiaoDto getRegiao() {
        return regiao;
    }
}