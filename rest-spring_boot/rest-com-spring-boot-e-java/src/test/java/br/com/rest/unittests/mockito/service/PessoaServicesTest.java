package br.com.rest.unittests.mockito.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rest.data.vo.v1.PessoaVO;
import br.com.rest.exception.RequiredObjectIsNullFoundException;
import br.com.rest.model.Pessoa;
import br.com.rest.repositories.PessoaRepository;
import br.com.rest.service.PessoaServices;
import br.com.rest.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PessoaServicesTest {
	
	MockPerson input;
	
	@InjectMocks
	private PessoaServices service;

	@Mock
	PessoaRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Pessoa entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/pessoa/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getEndereco());
		assertEquals("First Name Test1", result.getNome());
		assertEquals("Last Name Test1", result.getSobrenome());
		assertEquals("Female", result.getSexo());
	}
	

	@Test
	void testCriar() {
		Pessoa entity = input.mockEntity(1);
		entity.setId(1L);
		
		Pessoa persisted = entity;
		persisted.setId(1L);
		
		PessoaVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.criar(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/pessoa/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getEndereco());
		assertEquals("First Name Test1", result.getNome());
		assertEquals("Last Name Test1", result.getSobrenome());
		assertEquals("Female", result.getSexo());
	}
	
	@Test
	void testCriarComPessoaNulo() {
		
		Exception exception = assertThrows(RequiredObjectIsNullFoundException.class, () ->{service.criar(null);});
		
		String expectedMessege = "Não e permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessege));
		
	}
	
	

	@Test
	void testAtualizar() {
		Pessoa entity = input.mockEntity(1);
		
		
		Pessoa persisted = entity;
		persisted.setId(1L);
		
		PessoaVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.atualizar(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/pessoa/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getEndereco());
		assertEquals("First Name Test1", result.getNome());
		assertEquals("Last Name Test1", result.getSobrenome());
		assertEquals("Female", result.getSexo());
	}

	@Test
	void testAtualizarComPessoaNulo() {
		
		Exception exception = assertThrows(RequiredObjectIsNullFoundException.class, () ->{service.atualizar(null);});
		
		String expectedMessege = "Não e permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessege));
		
	}
	
	@Test
	void testDelete() {
		Pessoa entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	/*
	@Test
	void testFindAll() {
		List<Pessoa> list = input.mockEntityList();
		
		
		when(repository.findAll()).thenReturn(list);
		
		var pessoa = service.findAll();
		
		assertNotNull(pessoa);
		assertEquals(14, pessoa.size());
		
		var primeiraPessoa = pessoa.get(1);
		
		assertNotNull(primeiraPessoa);
		assertNotNull(primeiraPessoa.getKey());
		assertNotNull(primeiraPessoa.getLinks());
		
		assertTrue(primeiraPessoa.toString().contains("links: [</api/pessoa/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", primeiraPessoa.getEndereco());
		assertEquals("First Name Test1", primeiraPessoa.getNome());
		assertEquals("Last Name Test1", primeiraPessoa.getSobrenome());
		assertEquals("Female", primeiraPessoa.getSexo());
		
		var quartaPessoa = pessoa.get(4);
		
		assertNotNull(quartaPessoa);
		assertNotNull(quartaPessoa.getKey());
		assertNotNull(quartaPessoa.getLinks());
		
		assertTrue(quartaPessoa.toString().contains("links: [</api/pessoa/v1/4>;rel=\"self\"]"));
		assertEquals("Addres Test4", quartaPessoa.getEndereco());
		assertEquals("First Name Test4", quartaPessoa.getNome());
		assertEquals("Last Name Test4", quartaPessoa.getSobrenome());
		assertEquals("Male", quartaPessoa.getSexo());
		
		var setimaPessoa = pessoa.get(7);
		
		assertNotNull(setimaPessoa);
		assertNotNull(setimaPessoa.getKey());
		assertNotNull(setimaPessoa.getLinks());
		
		assertTrue(setimaPessoa.toString().contains("links: [</api/pessoa/v1/7>;rel=\"self\"]"));
		assertEquals("Addres Test7", setimaPessoa.getEndereco());
		assertEquals("First Name Test7", setimaPessoa.getNome());
		assertEquals("Last Name Test7", setimaPessoa.getSobrenome());
		assertEquals("Female", setimaPessoa.getSexo());
		
	}
*/
}
