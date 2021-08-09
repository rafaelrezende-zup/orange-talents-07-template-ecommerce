package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.enumeration.StatusPagamento;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    @NotBlank
    private String idPagamentoGateway;

    private LocalDateTime dataRegistro = LocalDateTime.now();

    @ManyToOne
    private Compra compra;

    public Pagamento(StatusPagamento status, String idPagamento, Compra compra) {
        this.status = status;
        this.idPagamentoGateway = idPagamento;
        this.compra = compra;
        this.dataRegistro = LocalDateTime.now();
    }

    @Deprecated
    public Pagamento() {
    }

    public boolean pagamentoComSucesso() {
        return this.status.equals(StatusPagamento.SUCESSO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return status == pagamento.status && Objects.equals(idPagamentoGateway, pagamento.idPagamentoGateway) && Objects.equals(dataRegistro, pagamento.dataRegistro) && Objects.equals(compra, pagamento.compra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, idPagamentoGateway, dataRegistro, compra);
    }
}
