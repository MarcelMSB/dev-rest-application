package dev.marcel.application.common.feign.core;

import javax.enterprise.util.AnnotationLiteral;

public class FeignConfigTypeLiteral extends AnnotationLiteral<FeignConfigType> implements FeignConfigType {

    private final String value;

    private FeignConfigTypeLiteral(String value) {
        this.value = value;
    }

    public static FeignConfigTypeLiteral of(final String value) {
        return new FeignConfigTypeLiteral(value);
    }

    @Override
    public String value() {
        return value;
    }

}
