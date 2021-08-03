package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.dto.NovaCategoriaDTO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique=true)
    private String nome;

    @ManyToOne
    private Categoria categoriaMae;

    public Categoria(NovaCategoriaDTO dto) {
        this.nome = dto.getNome();
    }

    @Deprecated
    public Categoria() {
    }

    public void setCategoriaMae(Categoria categoriaMae) {
        this.categoriaMae = categoriaMae;
    }
}
