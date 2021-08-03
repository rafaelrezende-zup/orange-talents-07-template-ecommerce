package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
