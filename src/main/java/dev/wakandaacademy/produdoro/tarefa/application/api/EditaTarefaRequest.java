package dev.wakandaacademy.produdoro.tarefa.application.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EditaTarefaRequest {
	@NotBlank
	@Size(message = "Campo descrição tarefa não pode estar vazio", max = 255, min = 3)
	private String descricao;

}
