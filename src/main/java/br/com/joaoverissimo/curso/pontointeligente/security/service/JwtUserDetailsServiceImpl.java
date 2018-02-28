package br.com.joaoverissimo.curso.pontointeligente.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.joaoverissimo.curso.pontointeligente.entities.Funcionario;
import br.com.joaoverissimo.curso.pontointeligente.security.JwtUserFactory;
import br.com.joaoverissimo.curso.pontointeligente.service.FuncionarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private FuncionarioService funcionarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(username);

        if (funcionario.isPresent()) {
            return JwtUserFactory.create(funcionario.get());
        }

        throw new UsernameNotFoundException("Email não encontrado.");
    }

}
