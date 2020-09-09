package dev.marcel.application.web.resource.produto;

import dev.marcel.application.model.produto.ProdutoRepository;
import dev.marcel.application.web.dto.produto.ProdutoDto;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.marcel.application.model.produto.Produto;
import dev.marcel.application.model.produto.ProdutoService;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    private ProdutoDto.RepresentationBuilder builder;
    @Inject
    private ProdutoRepository repository;
    @Inject
    private ProdutoService service;

    private Produto getProduto(Long id) {
        return repository.findByOrElseThrow(id);
    }

    @GET
    public Response findAll() {
        return Response.ok(builder.toRepresentation(repository.findAll())).build();
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        return Response.ok(builder.toRepresentation(getProduto(id))).build();
    }

    @POST
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response create(ProdutoDto dto) {
        Produto newProduto = service.persist(builder.fromRepresentation(dto, Produto.Builder.create()));
        return Response.status(201).entity(builder.toRepresentation(newProduto)).build();
    }

    @PUT
    @Path("{id}")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response update(@PathParam("id") Long id, ProdutoDto dto) {
        Produto produtoAtualizado = service.merge(builder.fromRepresentation(dto, Produto.Builder.from(getProduto(id))));
        return Response.accepted(builder.toRepresentation(produtoAtualizado)).build();
    }

    @DELETE
    @Path("{id}")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response delete(@PathParam("id") Long id) {
        service.remove(getProduto(id));
        return Response.noContent().build();
    }
}
