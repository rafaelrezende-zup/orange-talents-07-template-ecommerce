package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
