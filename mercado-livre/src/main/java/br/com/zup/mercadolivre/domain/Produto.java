package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.dto.NovoProdutoDTO;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal valor;

    @NotNull
    @Min(0)
    private Integer quantidade;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<@Valid Caracteristica> caracteristicas = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    private LocalDateTime dataResgistro;

    @ManyToOne
    private Usuario usuario;

    public Produto(NovoProdutoDTO dto, Categoria categoria, Usuario usuario) {
        this.nome = dto.getNome();
        this.valor = dto.getValor();
        this.quantidade = dto.getQuantidade();
        this.descricao = dto.getDescricao();
        this.caracteristicas = dto.getCaracteristicas().stream().map(c -> c.toModel(this)).collect(Collectors.toSet());
        this.categoria = categoria;
        this.dataResgistro = LocalDateTime.now();
        this.usuario = usuario;
    }

    @Deprecated
    public Produto() {
    }

}
