package dev.marcel.application.model.ibge.feign;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import dev.marcel.application.model.ibge.feign.dto.EstadoDto;

public interface IBGEFeignService {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("estados")
    List<EstadoDto> getEstados();
}
