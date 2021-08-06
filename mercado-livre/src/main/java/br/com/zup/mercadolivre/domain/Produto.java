package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.dto.NovaCaracteristicaDTO;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.OptionalDouble;
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
    @Positive
    private Integer quantidade;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto", cascade = CascadeType.PERSIST)
    private final Set<@Valid Caracteristica> caracteristicas = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    private LocalDateTime dataResgistro = LocalDateTime.now();

    @ManyToOne
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<Opiniao> opinioes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<Pergunta> perguntas = new HashSet<>();

    public Produto(@NotBlank String nome, @Positive int quantidade,
                   @NotBlank @Size(max = 1000) String descricao,
                   @NotNull @Positive BigDecimal valor,
                   @NotNull @Valid Categoria categoria, @NotNull @Valid Usuario usuario,
                   @Size(min = 3) @Valid Collection<NovaCaracteristicaDTO> caracteristicas) {

        this.nome = nome;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.dataResgistro = LocalDateTime.now();
        this.usuario = usuario;
        this.caracteristicas.addAll(caracteristicas
                .stream().map(caracteristica -> caracteristica.toModel(this))
                .collect(Collectors.toSet()));

        Assert.isTrue(this.caracteristicas.size() >= 3,"Todo produto precisa ter no mínimo 3 ou mais características");

    }

    @Deprecated
    public Produto() {
    }

    public void setImagens(Set<ImagemProduto> imagens) {
        this.imagens = imagens;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    public Set<ImagemProduto> associaImagens(Set<String> links) {
        return links.stream()
                .map(i -> new ImagemProduto(this, i))
                .collect(Collectors.toSet());
    }

    public boolean pertenceAoUsuario(Usuario possivelUsuario) {
        return this.usuario.equals(possivelUsuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<ImagemProduto> getImagens() {
        return imagens;
    }

    public Set<Opiniao> getOpinioes() {
        return opinioes;
    }

    public Set<Pergunta> getPerguntas() {
        return perguntas;
    }

    public Double calculaNotaMedia() {
        OptionalDouble media = opinioes.stream().map(Opiniao::getNota).mapToDouble(Integer::doubleValue).average();
        if (media.isPresent()){
            return media.getAsDouble();
        }
        return 0.0;
    }

    public Long getQtdNota() {
        return opinioes.stream().map(Opiniao::getNota).count();
    }
}
