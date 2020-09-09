package dev.marcel.application.web.resource.common.jaxrs;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper extends BaseValidationExceptionMapper<ConstraintViolationException> {
    
    @Override
    public Response toResponse(ConstraintViolationException e) {
        return super.toResponse(e);
    }
}
