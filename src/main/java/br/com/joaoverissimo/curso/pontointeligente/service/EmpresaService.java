package br.com.joaoverissimo.curso.pontointeligente.service;

import java.util.Optional;

import br.com.joaoverissimo.curso.pontointeligente.entities.Empresa;

public interface EmpresaService {

    Optional<Empresa> buscarPorCnpj(String cnpj);

    Empresa persistir(Empresa empresa);

}
