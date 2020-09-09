package dev.marcel.application.common.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;

import static feign.FeignException.errorStatus;

import dev.marcel.application.common.feign.exception.FeignClientException;
import dev.marcel.application.common.feign.exception.FeignServerException;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new FeignClientException(response.status(), response.reason(), response.body());
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new FeignServerException(response.status(), response.reason(), response.body());
        }
        return errorStatus(methodKey, response);
    }
}
