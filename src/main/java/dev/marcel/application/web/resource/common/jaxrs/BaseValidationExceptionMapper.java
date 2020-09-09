
package dev.marcel.application.web.resource.common.jaxrs;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Este mapper Ã© executado especificamente para exceptions de validaÃ§ao.
 *
 * @param <E>
 */
public class BaseValidationExceptionMapper<E extends Exception> implements ExceptionMapper<E> {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BaseValidationExceptionMapper.class);

    @Override
    public Response toResponse(E e) {

        String message = "Problema no processamento da requisição";
        Map<String, String> errorMap;

        final Set<ValidationMessage<?>> messages = ValidationUtil.getValidationResult(e).getMessages();

        if (!messages.isEmpty()) {
            errorMap = new HashMap<String, String>(messages.size());

            for (ValidationMessage<?> m : messages) {
                String errorPath = "message";
                if (m.getBean() != null && !"".equals(m.getBean())) {
                    errorPath = m.getBean();
                    if (m.getProperty() != null && !"".equals(m.getProperty())) {
                        errorPath += "." + m.getProperty();
                    }
                }
                errorMap.put(errorPath, m.getMessage());
            }
        } else {
            errorMap = Collections.emptyMap();
        }

        final ApiError error = new ApiError(message, errorMap);
        if (LOG.isDebugEnabled()) {
            LOG.debug(error, e);
        }
        return Response.status(422).entity(error).build();
    }
}

