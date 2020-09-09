package dev.marcel.application.common.feign.core;

import feign.Feign;
import feign.Request;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;

import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Vetoed;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import java.io.IOException;

import dev.marcel.application.common.feign.config.FeignConfig;
import dev.marcel.application.common.feign.decoder.FeignErrorDecoder;
import dev.marcel.application.common.feign.encoder.FeignEncoder;
import dev.marcel.application.common.jackson.JacksonObjectMapperContextResolver;

@RequestScoped
public class FeignRequest {

    private Logger LOGGER = Logger.getLogger(FeignRequest.class);

    @Inject
    private JacksonObjectMapperContextResolver contextResolver;

    public <T> T create(final Class<T> resource, final String configName) {
        final Config config = getConfig(configName);
        final Feign.Builder builder = config("feign.core" + configName);
        return builder.target(resource, config.getHost());
    }

    private Feign.Builder config(String idLog) {
        return new Feign.Builder()
                .contract(new JAXRSContract())
                .encoder(new FeignEncoder(new JacksonEncoder(contextResolver.createObjectMapper())))
                .decoder(new JacksonDecoder(contextResolver.createObjectMapper()))
                .errorDecoder(new FeignErrorDecoder())
                .logger(new FeignRequestLogger(Logger.getLogger(idLog)))
                .logLevel(feign.Logger.Level.FULL);
    }

    private Config getConfig(final String configName) {
        FeignConfig feignConfig = null;
        try {
            feignConfig = CDI.current().select(FeignConfig.class, FeignConfigTypeLiteral.of(configName)).get();
            return Config.of(feignConfig.getHost());
        } catch (Exception e) {
            LOGGER.error("Não foi possível localizar a configuracao da " + configName, e);
            throw new RuntimeException(e);
        } finally {
            CDI.current().destroy(feignConfig);
        }
    }

    @Vetoed
    private static class FeignRequestLogger extends feign.Logger {

        private final Logger logger;

        public FeignRequestLogger(Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void logRequest(String configKey, feign.Logger.Level logLevel, Request request) {
            super.logRequest(configKey, logLevel, request);
        }

        @Override
        protected Response logAndRebufferResponse(String configKey, feign.Logger.Level logLevel, Response response,
                long elapsedTime) throws IOException {
            return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
        }

        @Override
        protected void log(String configKey, String format, Object... args) {
            logger.info(String.format(methodTag(configKey) + format, args));
        }
    }

    public static final class Config {

        private final String host;

        private Config(String host) {
            this.host = host;
        }

        public static Config of(final String host) {
            return new Config(host);
        }

        public String getHost() {
            return host;
        }
    }
}
