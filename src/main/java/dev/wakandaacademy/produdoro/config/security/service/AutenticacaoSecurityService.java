package dev.wakandaacademy.produdoro.config.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.wakandaacademy.produdoro.credencial.application.repository.CredencialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutenticacaoSecurityService implements UserDetailsService {
    private final CredencialRepository credencialRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        log.info("[inicio] AutenticacaoSecurityService - buscando usuario pelo nome");
        var credencial = credencialRepository.buscaCredencialPorUsuario(usuario);
        log.info("[finaliza] AutenticacaoSecurityService - buscando usuario pelo nome");
        return Optional.ofNullable(credencial).orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
