package dev.wakandaacademy.produdoro.tarefa.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.wakandaacademy.produdoro.config.security.service.TokenService;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.service.TarefaService;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class TarefaRestController implements TarefaAPI {
	private final TarefaService tarefaService;
	private final TokenService tokenService;

	public TarefaIdResponse postNovaTarefa(TarefaRequest tarefaRequest) {
		log.info("[inicia]  TarefaRestController - postNovaTarefa  ");
		TarefaIdResponse tarefaCriada = tarefaService.criaNovaTarefa(tarefaRequest);
		log.info("[finaliza]  TarefaRestController - postNovaTarefa");
		return tarefaCriada;
	}

	@Override
	public TarefaDetalhadoResponse detalhaTarefa(String token, UUID idTarefa) {
		log.info("[inicia] TarefaRestController - detalhaTarefa");
		String usuario = getUsuarioByToken(token);
		Tarefa tarefa = tarefaService.detalhaTarefa(usuario, idTarefa);
		log.info("[finaliza] TarefaRestController - detalhaTarefa");
		return new TarefaDetalhadoResponse(tarefa);
	}

	private String getUsuarioByToken(String token) {
		log.debug("[token] {}", token);
		String usuario = tokenService.getUsuarioByBearerToken(token)
				.orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, token));
		log.info("[usuario] {}", usuario);
		return usuario;
	}

	@Override
	public void editaTarefa(String token, UUID idTarefa, EditaTarefaRequest tarefaRequest) {
		log.info("[inicia] TarefaRestController - editaTarefa");
		String emailUsuario = getUsuarioByToken(token);
		tarefaService.editaTarefa(emailUsuario, idTarefa, tarefaRequest);
		log.info("[finaliza] TarefaRestController - editaTarefa");
	}
	public List<TarefaListResponse> buscaTarefasPorUsuario(String token, UUID idUsuario) {
		log.info("[inicia] TarefaRestController - buscaTarefasPorUsuario");
		String usuario = getUsuarioByToken(token);
		List<TarefaListResponse> tarefas = tarefaService.buscaTarefasPorUsuario(usuario, idUsuario);
		log.info("[finaliza] TarefaRestController - buscaTarefasPorUsuario");
		return tarefas;
	}
		
	public void concluiTarefa(String token, UUID idTarefa) {
		log.info("[inicia] TarefaRestController - concluiTarefa");
		String emailUsuario = getUsuarioByToken(token);
		tarefaService.concluiTarefa(emailUsuario, idTarefa);
		log.info("[finaliza] TarefaRestController - concluiTarefa");

	}

	@Override
	public void deletaTodasAsTarefasDoUsuario(String token, UUID idUsuario) {
		log.info("[inicia] TarefaRestController - deletaTodasAsTarefasDoUsuario");
		String emailUsuario = getUsuarioByToken(token);
		tarefaService.deletaTodasAsTarefasDoUsuario(emailUsuario, idUsuario);
		log.info("[finaliza] TarefaRestController - deletaTodasAsTarefasDoUsuario");

	}

}
