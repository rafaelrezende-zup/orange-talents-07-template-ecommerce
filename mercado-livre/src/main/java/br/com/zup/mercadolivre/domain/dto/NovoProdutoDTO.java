package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Categoria;
import br.com.zup.mercadolivre.domain.Produto;
import br.com.zup.mercadolivre.domain.Usuario;
import br.com.zup.mercadolivre.validator.Exist;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NovoProdutoDTO {

    @NotBlank
    private String nome;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal valor;

    @NotNull
    @Min(0)
    private Integer quantidade;

    @Size(min = 3, message = "O produto deve ter no mínimo 3 características")
    private List<@Valid NovaCaracteristicaDTO> caracteristicas = new ArrayList<>();

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotNull
    @Exist(domainClass = Categoria.class, message = "Categoria não encontrada")
    private Long idCategoria;

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public List<NovaCaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public Produto toModel(Usuario usuario, Categoria categoria) {
        return new Produto(nome, quantidade, descricao, valor, categoria, usuario,
                caracteristicas);
    }

    public Set<String> buscaCaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>();
        HashSet<String> resultados = new HashSet<>();
        for (NovaCaracteristicaDTO caracteristica : caracteristicas) {
            String nome = caracteristica.getNome();
            if (!nomesIguais.add(nome)) {
                resultados.add(nome);
            }
        }
        return resultados;
    }

}
