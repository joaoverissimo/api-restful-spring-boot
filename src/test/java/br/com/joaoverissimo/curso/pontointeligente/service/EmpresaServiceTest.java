package br.com.joaoverissimo.curso.pontointeligente.service;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.joaoverissimo.curso.pontointeligente.entities.Empresa;
import br.com.joaoverissimo.curso.pontointeligente.repositories.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    // @InjectMocks
    private EmpresaService empresaService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(empresaRepository.findByCnpj(Mockito.anyString())).thenReturn(new Empresa());
        Mockito.when(empresaRepository.save(Mockito.any(Empresa.class))).thenReturn(new Empresa());
    }

    @Test
    public void deveBuscarEmpresaPorCNPJ() {
        Optional<Empresa> empresa = empresaService.buscarPorCnpj("423424242234");

        Assert.assertTrue(empresa.isPresent());
    }

    @Test
    public void devePersistirEmpresa() {
        Empresa empresa = empresaService.persistir(new Empresa());

        assertNotNull(empresa);
    }

}
