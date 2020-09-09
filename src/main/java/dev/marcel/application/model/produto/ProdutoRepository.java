package dev.marcel.application.model.produto;

import javax.enterprise.context.RequestScoped;

import dev.marcel.application.model.common.repository.AbstractRepository;

@RequestScoped
public class ProdutoRepository extends AbstractRepository<Produto> {
    
    public boolean existsByBusinessKey(final Produto produto){
        return from(entityPath).where(QProduto.produto.eqBusinessKey(produto)).exists();
    }
}
