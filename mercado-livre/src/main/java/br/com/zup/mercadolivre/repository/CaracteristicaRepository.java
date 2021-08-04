package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.domain.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
}
