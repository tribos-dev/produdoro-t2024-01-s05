package dev.wakandaacademy.produdoro.tarefa.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.api.EditaTarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaListResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class TarefaApplicationService implements TarefaService {
	private final TarefaRepository tarefaRepository;
	private final UsuarioRepository usuarioRepository;

	@Override
	public TarefaIdResponse criaNovaTarefa(TarefaRequest tarefaRequest) {
		log.info("[inicia] TarefaApplicationService - criaNovaTarefa");
		Tarefa tarefaCriada = tarefaRepository.salva(new Tarefa(tarefaRequest));
		log.info("[finaliza] TarefaApplicationService - criaNovaTarefa");
		return TarefaIdResponse.builder().idTarefa(tarefaCriada.getIdTarefa()).build();
	}

	@Override
	public void ativaTarefa(String usuario, UUID idTarefa) {
		log.info("[inicia] TarefaApplicationService - ativaTarefa");
		Tarefa tarefa = detalhaTarefa(usuario, idTarefa);
		tarefaRepository.desativaTarefasId(tarefa.getIdUsuario());
		tarefa.ativaTarefa();
		tarefaRepository.salva(tarefa);
		log.info("[finaliza] TarefaApplicationService - ativaTarefa");
	}

	@Override
	public Tarefa detalhaTarefa(String usuario, UUID idTarefa) {
		log.info("[inicia] TarefaApplicationService - detalhaTarefa");
		Usuario usuarioPorEmail = usuarioRepository.buscaUsuarioPorEmail(usuario);
		log.info("[usuarioPorEmail] {}", usuarioPorEmail);
		Tarefa tarefa = tarefaRepository.buscaTarefaPorId(idTarefa)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
		log.info("[finaliza] TarefaApplicationService - detalhaTarefa");
		return tarefa;
	}

	@Override
	public void editaTarefa(String emailUsuario, UUID idTarefa, EditaTarefaRequest tarefaRequest) {
		log.info("[inicia] TarefaApplicationService - editaTarefa");
		Tarefa tarefa = detalhaTarefa(emailUsuario, idTarefa);
		tarefa.edita(tarefaRequest);
		tarefaRepository.salva(tarefa);
		log.info("[finaliza] TarefaApplicationService - editaTarefa");
	}

	@Override
	public List<TarefaListResponse> buscaTarefasPorUsuario(String usuario, UUID idUsuario) {
		log.info("[inicia] TarefaApplicationService - buscaTarefasPorUsuario");
		Usuario usuarioPorEmail = usuarioRepository.buscaUsuarioPorEmail(usuario);
		usuarioRepository.buscaUsuarioPorId(idUsuario);
		usuarioPorEmail.validaUsuario(idUsuario);
		List<Tarefa> tarefas = tarefaRepository.buscaTarefasPorUsuario(idUsuario);
		log.info("[finaliza] TarefaApplicationService - buscaTarefasPorUsuario");
		return TarefaListResponse.converte(tarefas);
	}

	@Override
	public void concluiTarefa(String emailUsuario, UUID idTarefa) {
		log.info("[inicia] TarefaApplicationService - concluiTarefa");
		Tarefa tarefa = detalhaTarefa(emailUsuario, idTarefa);
		tarefa.concluiTarefa();
		tarefaRepository.salva(tarefa);
		log.info("[inicia] TarefaApplicationService - concluiTarefa");
	}

	@Override
	public void deletaTodasAsTarefasDoUsuario(String emailUsuario, UUID idUsuario) {
		log.info("[inicia] TarefaApplicationService - deletaTodasAsTarefasDoUsuario");
		Usuario usuarioPorEmail = usuarioRepository.buscaUsuarioPorEmail(emailUsuario);
		log.info("[usuarioPorEmail] {}", usuarioPorEmail);
		Usuario usuario = usuarioRepository.buscaUsuarioPorId(idUsuario);
		usuario.pertenceAoUsuario(usuarioPorEmail);
		List<Tarefa> tarefasUsuario = tarefaRepository.buscaTarefasPorUsuario(usuario.getIdUsuario());
		if (tarefasUsuario.isEmpty()) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Usuário não possui tarefa(as) cadastrada(as)");
		}
		tarefaRepository.deletaTodasAsTarefasDoUsuario(tarefasUsuario);
		log.info("[finaliza] TarefaApplicationService - deletaTodasAsTarefasDoUsuario");
	}

	@Override
	public void deletaTarefasConcluidas(String usuarioEmail, UUID idUsuario) {
		log.info("[inicia] TarefaApplicationService - deletaTarefaconcluida");	
		usuarioRepository.buscaUsuarioPorId(idUsuario);
		Usuario usuario = usuarioRepository.buscaUsuarioPorEmail(usuarioEmail);
		log.info("[usuarioPorEmail] {}", usuario);
		usuario.validaUsuario(idUsuario);
		boolean tarefasExcluidas = tarefaRepository.deletaConcluidas(idUsuario, StatusTarefa.CONCLUIDA);
		log.info("[tarefasExcluidas] {}", tarefasExcluidas);
		if (!tarefasExcluidas) {          
            throw APIException.build(HttpStatus.NOT_FOUND, "Usuário não possui nenhuma tarefa concluída!");
        }
		log.info("[finaliza] TarefaApplicationService - deletaTarefaconcluida");
	}
}
