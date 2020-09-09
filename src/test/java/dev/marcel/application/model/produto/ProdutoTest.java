package dev.marcel.application.model.produto;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

import javax.enterprise.inject.Produces;

import org.jglue.cdiunit.CdiRunner;

import org.junit.runner.RunWith;

import javax.validation.ConstraintViolationException;

import org.junit.Test;

@RunWith(CdiRunner.class)
public class ProdutoTest {

    @Produces
    private ProdutoRepository produtoRepositoryProducer() {
        return mock(ProdutoRepository.class, RETURNS_DEEP_STUBS);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCriacaoProdutoSemDadosObrigatorios() {
        Produto.Builder.create().build();
    }
}
