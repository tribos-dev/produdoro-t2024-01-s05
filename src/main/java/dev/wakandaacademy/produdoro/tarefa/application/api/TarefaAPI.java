package dev.wakandaacademy.produdoro.tarefa.application.api;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tarefa")
public interface TarefaAPI {
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    TarefaIdResponse postNovaTarefa(@RequestBody @Valid TarefaRequest tarefaRequest);

    @GetMapping("/{idTarefa}")
    @ResponseStatus(code = HttpStatus.OK)
    TarefaDetalhadoResponse detalhaTarefa(@RequestHeader(name = "Authorization",required = true) String token, 
    		@PathVariable UUID idTarefa);
    @PatchMapping("/usuario-modifica-a-ordem-de-uma-tarefa/{idTarefa}/{linha}")
    @ResponseStatus(code=HttpStatus.ACCEPTED)
    TarefaIdResponse mudaOrdemDeUmaTarefa(@RequestHeader(name = "Authorization",required=true) String token,
                                          @PathVariable UUID idTarefa,@PathVariable int linha);


}
