package dev.wakandaacademy.produdoro.pomodoro.application.service;

import org.springframework.stereotype.Service;

import dev.wakandaacademy.produdoro.pomodoro.domain.ConfiguracaoPadrao;

@Service
public class PomodoroService implements PomodoroApplicationService {
	
	@Override
	public ConfiguracaoPadrao getConfiguracaoPadrao() {
		//TODO Implementar busca das configuracoes padroes no MongoDB
		return ConfiguracaoPadrao.builder()
			.tempoMinutosFoco(25)
			.tempoMinutosPausaCurta(5)
			.tempoMinutosPausaLonga(15)
			.repeticoesParaPausaLonga(3)
			.build();
		}
}
