package dev.marcel.application.web.resource.common.jaxrs;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper extends BaseValidationExceptionMapper<ValidationException> {
    
    @Override
    public Response toResponse(ValidationException e) {
        return super.toResponse(e);
    }
}
