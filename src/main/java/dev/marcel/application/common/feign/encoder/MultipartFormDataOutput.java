package dev.marcel.application.common.feign.encoder;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class MultipartFormDataOutput {

    private final Map<String, Object> entity;

    private MultipartFormDataOutput(final Map<String, Object> entity) {
        this.entity = entity;
    }

    public static MultipartFormDataOutput of(final Map<String, Object> entity) {
        return new MultipartFormDataOutput(entity);
    }

    public static MultipartFormDataOutput ofEmpty() {
        return new MultipartFormDataOutput(new HashMap<>());
    }
    
    public MultipartFormDataOutput add(String key, Object value){
        entity.put(key, value);
        return this;
    }
    
    public ImmutableMap<String, Object> asMap() {
        return ImmutableMap.copyOf(entity);
    }
}
