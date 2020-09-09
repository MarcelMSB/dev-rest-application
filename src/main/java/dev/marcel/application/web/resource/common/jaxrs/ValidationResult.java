package dev.marcel.application.web.resource.common.jaxrs;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public final class ValidationResult {

    private ImmutableSet<ValidationMessage<?>> messages;
    
    private ValidationResult(final ImmutableSet<ValidationMessage<?>> messages) {
        this.messages = messages;
    }

    public static ValidationResult of() {
        return of(ImmutableSet.<ValidationMessage<?>>of());
    }

    public static ValidationResult of(final ValidationMessage<?>... messages) {
        return of(ImmutableSet.<ValidationMessage<?>>copyOf(messages));
    }

    public static ValidationResult of(final ImmutableSet<ValidationMessage<?>> messages) {
        return new ValidationResult(messages);
    }

    public ValidationResult merge(ValidationResult other) {
        if (other.getMessages().isEmpty()) {
            return this;
        }

        final Set<ValidationMessage<?>> validation = new LinkedHashSet<ValidationMessage<?>>(messages);
        validation.addAll(other.getMessages());

        messages = ImmutableSet.copyOf(validation);
        return this;
    }

    public boolean isValid() {
        return messages.isEmpty();
    }

    public ImmutableSet<ValidationMessage<?>> getMessages() {
        return messages;
    }
}
