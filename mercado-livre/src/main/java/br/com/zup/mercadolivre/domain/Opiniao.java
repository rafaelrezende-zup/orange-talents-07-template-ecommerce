package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.dto.NovaOpiniaoDTO;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    @Size(max = 500)
    @Column(length = 500)
    private String descricao;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Opiniao(@Valid NovaOpiniaoDTO dto,
                   @Valid Usuario usuario,
                   @Valid Produto produto) {
        this.nota = dto.getNota();
        this.titulo = dto.getTitulo();
        this.descricao = dto.getDescricao();
        this.usuario = usuario;
        this.produto = produto;
    }

    @Deprecated
    public Opiniao() {
    }

    public Long getId() {
        return id;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
