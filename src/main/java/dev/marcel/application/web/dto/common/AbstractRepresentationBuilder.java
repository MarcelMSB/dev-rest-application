package dev.marcel.application.web.dto.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepresentationBuilder<T, DTO, B> {

    protected abstract T fromRepresentation(DTO dto, B builder);

    protected abstract DTO toRepresentation(T t);

    public List<DTO> toRepresentation(List<T> lista) {
        final List<DTO> listaDto = new ArrayList<>();
        lista.stream().forEach(registro -> {
            listaDto.add(this.toRepresentation(registro));
        });
        return listaDto;
    }
}
