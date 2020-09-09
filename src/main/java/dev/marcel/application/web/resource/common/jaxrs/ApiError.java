package dev.marcel.application.web.resource.common.jaxrs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@XmlAccessorType(XmlAccessType.NONE)
public class ApiError {

    @XmlElement(name = "message")
    private String message;
    @XmlElement(name = "classificationCode")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String classificationCode;
    @XmlElement(name = "detail")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Map detail;

    public ApiError(final String message) {
        this(message, null, null);
    }

    public ApiError(final String message, final String classificationCode, final Map detail) {
        this.message = message != null ? message : "null";
        this.classificationCode = classificationCode;
        this.detail = detail;
    }

    public ApiError(final String message, final String classificationCode) {
        this(message, classificationCode, null);
    }

    public ApiError(final String message, final Map detail) {
        this(message, null, detail);
    }

    private ApiError() {
    }

    public Map getDetail() {
        return detail;
    }

    public String getMessage() {
        return message;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    @Override
    public String toString() {
        return "ApiError(message=" + this.getMessage() + ")";
    }

    public static class Builder {

        private final ApiError apiError = new ApiError();

        public Builder message(final String message) {
            apiError.message = message;
            return this;
        }

        public Builder classificationCode(final String classificationCode) {
            apiError.classificationCode = classificationCode;
            return this;
        }

        public Builder detail(final Map detail) {
            apiError.detail = detail;
            return this;
        }

        public ApiError build() {
            return apiError;
        }
    }
}
