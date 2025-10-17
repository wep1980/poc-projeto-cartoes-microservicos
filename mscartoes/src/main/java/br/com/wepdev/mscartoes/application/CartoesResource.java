package br.com.wepdev.mscartoes.application;

import br.com.wepdev.mscartoes.application.representation.CartaoSaveRequestDTO;
import br.com.wepdev.mscartoes.application.representation.CartoesPorClienteResponseDTO;
import br.com.wepdev.mscartoes.domain.Cartao;
import br.com.wepdev.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;


    @GetMapping
    public String status(){
        return "ok";
    }


    @PostMapping
    public ResponseEntity cadastra( @RequestBody CartaoSaveRequestDTO request ){

        Cartao cartao = request.toModel();
        cartaoService.save(cartao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda){

        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);

        return ResponseEntity.ok(list);
    }


    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponseDTO>> getCartoesByCliente(@RequestParam("cpf") String cpf){

        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);

        List<CartoesPorClienteResponseDTO> resultList = lista.stream()
                .map(CartoesPorClienteResponseDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }

}
