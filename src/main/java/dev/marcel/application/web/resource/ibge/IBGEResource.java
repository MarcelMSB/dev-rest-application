package dev.marcel.application.web.resource.ibge;

import javax.ejb.Stateless;
import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.marcel.application.model.ibge.feign.IBGEFeignService;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IBGEResource {

    @Inject
    private IBGEFeignService service;

    @GET
    @Path("estados")
    public Response findEstados() {
        return Response.ok(service.getEstados()).build();
    }
}