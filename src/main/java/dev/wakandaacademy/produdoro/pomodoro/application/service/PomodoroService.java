package dev.wakandaacademy.produdoro.pomodoro.application.service;

import dev.wakandaacademy.produdoro.pomodoro.domain.ConfiguracaoPadrao;

public class PomodoroService implements PomodoroApplicationService {
	
	@Override
	public ConfiguracaoPadrao getConfiguracaoPadrao() {
		return ConfiguracaoPadrao.builder()
			.tempoMinutosFoco(25)
			.tempoMinutosPausaCurta(5)
			.tempoMinutosPausaLonga(15)
			.repeticoesParaPausaLonga(3)
			.build();
		}
}
