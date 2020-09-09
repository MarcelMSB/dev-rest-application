package dev.marcel.application.common.feign.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Qualifier;

@Stereotype
@RequestScoped
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE })
public @interface FeignConfigType {

    public String value();

}
