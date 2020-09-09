package dev.marcel.application.model.ibge.feign.dto;

public class RegiaoDto {

    private Long id;
    private String nome;
    private String sigla;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }
}