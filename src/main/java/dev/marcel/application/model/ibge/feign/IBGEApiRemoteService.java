package dev.marcel.application.model.ibge.feign;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import dev.marcel.application.common.feign.core.FeignRequest;
import dev.marcel.application.model.ibge.feign.dto.EstadoDto;

@RequestScoped
public class IBGEApiRemoteService implements IBGEFeignService {

    public static final String IBGE_API_REMOTE = "IBGE_API_REMOTE";

    @Inject
    private FeignRequest feignRequest;

    public IBGEFeignService request() {
        return feignRequest.create(IBGEFeignService.class, IBGE_API_REMOTE);
    }

    @Override
    public List<EstadoDto> getEstados() {
        return request().getEstados();
    }
}
