package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.component.EnviaEmail;
import br.com.zup.mercadolivre.component.NovaCompraSucesso;
import br.com.zup.mercadolivre.domain.Compra;
import br.com.zup.mercadolivre.domain.dto.GatewayPagamentoDTO;
import br.com.zup.mercadolivre.domain.dto.PagamentoPagseguroDTO;
import br.com.zup.mercadolivre.domain.enumeration.StatusCompra;
import br.com.zup.mercadolivre.repository.CompraRepository;
import br.com.zup.mercadolivre.repository.PagamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    private final PagamentoRepository pagamentoRepository;
    private final CompraRepository compraRepository;
    private final NovaCompraSucesso novaCompraSucesso;
    private final EnviaEmail enviaEmail;

    public PagamentoController(PagamentoRepository pagamentoRepository,
                               CompraRepository compraRepository,
                               NovaCompraSucesso novaCompraSucesso,
                               EnviaEmail enviaEmail) {
        this.pagamentoRepository = pagamentoRepository;
        this.compraRepository = compraRepository;
        this.novaCompraSucesso = novaCompraSucesso;
        this.enviaEmail = enviaEmail;
    }

    @PostMapping(value = "/pagseguro/{id}")
    @Transactional
    public ResponseEntity<?> processaPagseguro(@PathVariable Long id, @RequestBody @Valid PagamentoPagseguroDTO dto) {
        return processaPagamento(id, dto);
    }

    @PostMapping(value = "/paypal/{id}")
    @Transactional
    public ResponseEntity<?> processaPaypal(@PathVariable Long id, @Valid PagamentoPagseguroDTO dto) {
        return processaPagamento(id, dto);
    }

    private ResponseEntity<?> processaPagamento(Long id, GatewayPagamentoDTO dto) {
        Optional<Compra> compra = compraRepository.findById(id);
        if (compra.isPresent() && compra.get().getStatus().equals(StatusCompra.INICIADA)) {
            try {
                compra.get().adicionaPagamento(dto);
                pagamentoRepository.save(dto.toPagamento(compra.get()));
                novaCompraSucesso.processa(compra.get());
                enviaEmail.enviaConfirmacaoPagamento(compra.get());
                return ResponseEntity.ok().build();
            } catch (RuntimeException e){
                enviaEmail.enviaErroPagamento(compra.get());
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
