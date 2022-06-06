package dev.wakandaacademy.produdoro.credencial.application.repository;

import dev.wakandaacademy.produdoro.credencial.domain.Credencial;

public interface CredencialRepository {
	Credencial salva(Credencial credencial);
	Credencial buscaCredencialPorUsuario(String usuario);
}
