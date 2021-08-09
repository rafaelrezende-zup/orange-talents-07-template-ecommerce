package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
