package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
