package dev.marcel.application.web.resource;

import javax.inject.Inject;
import javax.ws.rs.Path;
import dev.marcel.application.web.resource.produto.ProdutoResource;
import dev.marcel.application.web.resource.ibge.IBGEResource;

@Path("api")
public class ApiResource {

    @Inject
    private ProdutoResource produtoResource;
    @Inject
    private IBGEResource ibgeResource;

    @Path("produtos")
    public ProdutoResource produto() {
        return produtoResource;
    }

    @Path("ibge")
    public IBGEResource ibge() {
        return ibgeResource;
    }
}
