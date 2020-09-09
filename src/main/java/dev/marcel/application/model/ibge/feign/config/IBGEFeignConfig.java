package dev.marcel.application.model.ibge.feign.config;

import static dev.marcel.application.model.ibge.feign.IBGEApiRemoteService.IBGE_API_REMOTE;

import javax.annotation.Resource;

import dev.marcel.application.common.feign.config.FeignConfig;
import dev.marcel.application.common.feign.core.FeignConfigType;

@FeignConfigType(IBGE_API_REMOTE)
public class IBGEFeignConfig implements FeignConfig {

    @Resource(name = "java:global/ibge/host")
    private String host;

    @Override
    public String getHost() {
        return host.concat("/");
    }
}
