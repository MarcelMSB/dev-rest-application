package dev.marcel.application.web.dto.common;

public abstract class AbstractDtoBuilder<T> {

    protected T dto;

    protected AbstractDtoBuilder(T dto) {
        this.dto = dto;
    }

    public T build() {
        return dto;
    }
}
