package br.com.joaoverissimo.curso.pontointeligente.service;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.joaoverissimo.curso.pontointeligente.dto.CadastroPJDto;
import br.com.joaoverissimo.curso.pontointeligente.utils.ValidationUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MsgTest {

    private static Validator validator;

    @BeforeClass
    public static void setupClass() {
        validator = ValidationUtils.buildValidator();

    }

    @Test
    public void deveValidarNome() {
        CadastroPJDto obj = gerarPessoa();
        obj.setNome(null);

        Set<ConstraintViolation<CadastroPJDto>> constraintViolations = validator.validate(obj);

        assertEquals("Nome não pode ser vazio.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void deveSerEmailValido() {
        CadastroPJDto obj = gerarPessoa();
        obj.setEmail("não informado");

        Set<ConstraintViolation<CadastroPJDto>> constraintViolations = validator.validate(obj);

        assertEquals("Email inválido.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void deveValidarCNPJ() {
        CadastroPJDto obj = gerarPessoa();
        obj.setCnpj("123");

        Set<ConstraintViolation<CadastroPJDto>> constraintViolations = validator.validate(obj);

        assertEquals("CNPJ inválido.", constraintViolations.iterator().next().getMessage());
    }

    private CadastroPJDto gerarPessoa() {
        CadastroPJDto obj = new CadastroPJDto();
        obj.setSenha("asdf");
        obj.setRazaoSocial("Loja Fulano");
        obj.setCnpj("43.926.421/0001-50");
        obj.setEmail("jose@gmail.com");
        obj.setNome("José da Rocha");
        obj.setCpf("35325857231");
        return obj;
    }
}
