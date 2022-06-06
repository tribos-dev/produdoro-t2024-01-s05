package dev.wakandaacademy.produdoro.credencial.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "Perfil")
public class Perfil implements GrantedAuthority {
	@MongoId(targetType = FieldType.STRING)
	@Getter
	private String nome;

	@Override
	public String getAuthority() {
		return this.nome;
	}

	private static final long serialVersionUID = 1L;
}
