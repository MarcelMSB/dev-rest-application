package dev.marcel.application.common.feign.core;

public interface FeignService<T> {

    public T call();
}
