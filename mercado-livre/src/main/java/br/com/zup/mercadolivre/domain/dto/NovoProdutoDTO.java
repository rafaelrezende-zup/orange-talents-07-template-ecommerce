package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Categoria;
import br.com.zup.mercadolivre.validator.Exist;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
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
    private Set<@Valid NovaCaracteristicaDTO> caracteristicas;

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

    public Set<NovaCaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

}
