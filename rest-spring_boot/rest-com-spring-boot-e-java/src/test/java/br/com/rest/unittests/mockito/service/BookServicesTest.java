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

import br.com.rest.data.book.vo.BookVO;
import br.com.rest.exception.RequiredObjectIsNullFoundException;
import br.com.rest.model.book.Book;
import br.com.rest.repositories.book.BookRepository;
import br.com.rest.service.book.BookService;
import br.com.rest.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {
	
	MockBook input;
	
	@InjectMocks
	private BookService service;

	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
	

	@Test
	void testCriar() {
		Book entity = input.mockEntity(1);

		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setId(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.criar(vo);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void testCriarComBookNulo() {
		
		Exception exception = assertThrows(RequiredObjectIsNullFoundException.class, () ->{service.criar(null);});
		
		String expectedMessege = "Não e permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessege));
		
	}
	
	

	@Test
	void testAtualizar() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.atualizar(vo);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testAtualizarComBookNulo() {
		
		Exception exception = assertThrows(RequiredObjectIsNullFoundException.class, () ->{service.atualizar(null);});
		
		String expectedMessege = "Não e permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessege));
		
	}
	
	@Test
	void testDelete() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	/*
	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();
		
		
		when(repository.findAll()).thenReturn(list);
		
		var book = service.findAll();
		
		assertNotNull(book);
		assertEquals(14, book.size());
		
		var primeiroBook = book.get(1);
		
		assertNotNull(primeiroBook);
		assertNotNull(primeiroBook.getId());
		assertNotNull(primeiroBook.getLinks());
		
		assertTrue(primeiroBook.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", primeiroBook.getAuthor());
		assertEquals("Title Test1", primeiroBook.getTitle());
		assertEquals(25D, primeiroBook.getPrice());
		assertNotNull(primeiroBook.getLaunchDate());
		
		var quartoBook = book.get(4);
		
		assertNotNull(quartoBook);
		assertNotNull(quartoBook.getId());
		assertNotNull(quartoBook.getLinks());
		
		assertTrue(quartoBook.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Author Test4", quartoBook.getAuthor());
		assertEquals("Title Test4", quartoBook.getTitle());
		assertEquals(25D, quartoBook.getPrice());
		assertNotNull(quartoBook.getLaunchDate());
		
		var setimoBook = book.get(7);
		
		assertNotNull(setimoBook);
		assertNotNull(setimoBook.getId());
		assertNotNull(setimoBook.getLinks());
		
		assertTrue(setimoBook.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
		assertEquals("Author Test7", setimoBook.getAuthor());
		assertEquals("Title Test7", setimoBook.getTitle());
		assertEquals(25D, setimoBook.getPrice());
		assertNotNull(setimoBook.getLaunchDate());
		
	}
*/
}
