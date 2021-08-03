package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Categoria;
import br.com.zup.mercadolivre.validator.UniqueValue;

import javax.validation.constraints.NotBlank;

public class NovaCategoriaDTO {
    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome", message = "Já existe uma categoria com este nome.")
    private String nome;

    private Long idCategoriaMae;

    public String getNome() {
        return nome;
    }

    public Long getIdCategoriaMae() {
        return idCategoriaMae;
    }
}
