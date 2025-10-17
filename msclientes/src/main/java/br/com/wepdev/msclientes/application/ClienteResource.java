package br.com.wepdev.msclientes.application;

import br.com.wepdev.msclientes.application.representation.ClienteSaveRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {


    private final ClienteService service;


    @GetMapping
    public String status(){

        log.info("Obtendo o status do microservice de clientes");
        return "ok";
    }


    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequestDTO request){

        var cliente = request.toModel();

        service.save(cliente);

        /**
         * Construindo URL dinamica
         */
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}") // passando parametro
                .buildAndExpand(cliente.getCpf())
                .toUri();

        return ResponseEntity.created(headerLocation).build();
    }


    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){

        var cliente = service.getByCPF(cpf);

        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

}
