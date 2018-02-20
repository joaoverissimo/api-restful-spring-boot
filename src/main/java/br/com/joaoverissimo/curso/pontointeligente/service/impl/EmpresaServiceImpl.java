package br.com.joaoverissimo.curso.pontointeligente.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaoverissimo.curso.pontointeligente.entities.Empresa;
import br.com.joaoverissimo.curso.pontointeligente.repositories.EmpresaRepository;
import br.com.joaoverissimo.curso.pontointeligente.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCnpj(String cnpj) {
        return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

}
