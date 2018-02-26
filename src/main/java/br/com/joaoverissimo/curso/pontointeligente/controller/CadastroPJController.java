package br.com.joaoverissimo.curso.pontointeligente.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaoverissimo.curso.pontointeligente.dto.CadastroPJDto;
import br.com.joaoverissimo.curso.pontointeligente.entities.Empresa;
import br.com.joaoverissimo.curso.pontointeligente.entities.Funcionario;
import br.com.joaoverissimo.curso.pontointeligente.enums.PerfilEnum;
import br.com.joaoverissimo.curso.pontointeligente.service.EmpresaService;
import br.com.joaoverissimo.curso.pontointeligente.service.FuncionarioService;
import br.com.joaoverissimo.curso.pontointeligente.utils.PasswordUtils;
import br.com.joaoverissimo.curso.pontointeligente.utils.Response;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private Logger LOG = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
            BindingResult result) throws NoSuchAlgorithmException {
        LOG.info("Cadastrando PJ: {}", cadastroPJDto.toString());

        Response<CadastroPJDto> response = new Response<>();
        validarDadosExistentes(cadastroPJDto, result);
        Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
        Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);

        if (result.hasErrors()) {
            LOG.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        empresaService.persistir(empresa);
        funcionario.setEmpresa(empresa);
        funcionarioService.persistir(funcionario);
        response.setData(this.converterCadastroPJDto(funcionario));
        return ResponseEntity.ok(response);

    }

    private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {
        this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
                .ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente")));

        this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
                .ifPresent(fnc -> result.addError(new ObjectError("funcionario", "Funcionário já existente")));

        this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
                .ifPresent(fnc -> result.addError(new ObjectError("funcionario", "Email já existe")));
    }

    private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());

        return empresa;
    }

    private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result)
            throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));

        return funcionario;
    }

    private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());

        return cadastroPJDto;
    }

}
