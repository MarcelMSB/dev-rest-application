package dev.marcel.application.web.resource.common.jaxrs;

import java.sql.SQLException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static ValidationResult getValidationResult(final Exception e) {
        final ValidationMessage.Builder builder = ValidationMessage.Builder.create();
        Throwable cause = e;
        while (cause != null) {
            if (cause instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) cause;
                for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
                    String bean, property, message;
                    if (cv.getLeafBean() != null) {
                        bean = cv.getLeafBean().getClass().getSimpleName();
                    } else {
                        bean = cv.getRootBean().getClass().getSimpleName();
                    }
                    property = stringPathToProperty(cv.getPropertyPath().toString());
                    message = cv.getMessage();
                    builder.add(bean, property, message, cv);
                }
                break;
            } else if (cause instanceof javax.validation.ValidationException) {
                javax.validation.ValidationException ve = (javax.validation.ValidationException) cause;
                builder.add(ve.getMessage());
                break;
            } else if (cause instanceof SQLException) {
                SQLException sqle = (SQLException) cause;
                do {
                    builder.add(sqle.getMessage());
                    sqle = sqle.getNextException();
                } while (sqle != null);
                break;
            }
            cause = cause.getCause();
        }

        return builder.build();
    }

    /**
     * Retorna o valor da propriedade para o {@code path} informado.
     * <p>
     * Para uma {@code String} "path.to.property" serÃ¡ retornado "property".
     *
     * @param path caminho a ser analisado.
     *
     * @return valor da propriedade para o caminho informado.
     */
    static String stringPathToProperty(final String path) {
        String pathStr = path;

        int dot = pathStr.lastIndexOf('.');
        if (dot > 0 && !pathStr.endsWith(".")) {
            pathStr = pathStr.substring(dot + 1);
        }

        return pathStr;
    }
}