package dev.marcel.application.model.common.entity;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

public abstract class AbstractEntityBuilder<T, B extends AbstractEntityBuilder> {

    protected T entity;
    private static Validator validator;

    protected AbstractEntityBuilder(T entity) {
        this.entity = entity;
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    protected void validate() {
        final Set<ConstraintViolation<T>> validateEntity = validator.validate(entity);
        if (!validateEntity.isEmpty()) {
            final ConstraintViolation<T> message = validateEntity.iterator().next();
            throw new ConstraintViolationException(
                    message.getPropertyPath().toString().concat(": ").concat(message.getMessage()), validateEntity);
        }

        final Set<ConstraintViolation<AbstractEntityBuilder<T, B>>> validateBuilder = validator.validate(this);
        if (!validateBuilder.isEmpty()) {
            final ConstraintViolation<AbstractEntityBuilder<T, B>> message = validateBuilder.iterator().next();
            throw new ConstraintViolationException(
                    message.getPropertyPath().toString().concat(": ").concat(message.getMessage()), validateBuilder);
        }
    }

    protected void beforeValidate() {
    }

    protected void afterValidate() {
    }

    public T build() {
        beforeValidate();
        validate();
        afterValidate();
        return entity;
    }

}
