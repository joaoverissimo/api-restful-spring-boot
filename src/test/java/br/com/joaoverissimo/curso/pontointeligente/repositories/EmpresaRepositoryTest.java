package br.com.joaoverissimo.curso.pontointeligente.repositories;

import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.joaoverissimo.curso.pontointeligente.entities.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest  {
	
	private static final String CNPJ = "51463131313231";
			
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Before
	public void setup() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);
		empresaRepository.save(empresa);
	}
	
	@After
	public void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void deveRetornarVerdadeiro() {
		Empresa empresa = empresaRepository.findByCnpj(CNPJ);
		
		assertEquals(CNPJ, empresa.getCnpj());
	}
	
}
