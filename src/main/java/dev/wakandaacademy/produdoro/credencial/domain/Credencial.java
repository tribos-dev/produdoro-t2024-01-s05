package dev.wakandaacademy.produdoro.credencial.domain;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "Credencial")
public class Credencial implements UserDetails {
	@MongoId(targetType = FieldType.STRING)
	@Getter
	private String usuario;
	
	@NotNull
	@Size(max = 60)
	private String senha;
	
	@Getter
	private boolean validado;

	public Credencial(String usuario, @NotNull String senha) {
		this.usuario = usuario;
		var encriptador = new BCryptPasswordEncoder();
		this.senha = encriptador.encode(senha);
		this.validado = true;
	}

	public void encriptaSenha() {
		var encriptador = new BCryptPasswordEncoder();
		this.senha = encriptador.encode(this.senha);
	}
	
	public void validaCredencial() {
		this.validado = true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	private static final long serialVersionUID = 1L;
}
