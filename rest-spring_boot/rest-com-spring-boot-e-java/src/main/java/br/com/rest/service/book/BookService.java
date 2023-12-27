package br.com.rest.service.book;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.rest.controller.book.BookController;
import br.com.rest.data.book.vo.BookVO;
import br.com.rest.exception.RequiredObjectIsNullFoundException;
import br.com.rest.exception.ResourceNotFoundException;
import br.com.rest.mapper.DozerMapper;
import br.com.rest.model.book.Book;
import br.com.rest.repositories.book.BookRepository;


@Service
public class BookService {
private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	BookRepository repository;

	
	public List<BookVO> findAll() {
		logger.info("todas as pessoas");
	
		var persons = DozerMapper.parseListObject(repository.findAll(), BookVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel()));
		
		return persons;
	}
	


	public BookVO findById(Long id) {
		logger.info("Encontrando uma Pessoa!");
		
		var entity = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		var vo = DozerMapper.parseObject(entity,BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}

	
	
	public BookVO criar(BookVO book) {
		
		if(book == null) throw new RequiredObjectIsNullFoundException();
		logger.info("criando um Book");
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel());
		return vo;
	}
	
	
	public BookVO atualizar(BookVO book) {
		
		if(book == null) throw new RequiredObjectIsNullFoundException();
		
		logger.info("atualizando um Book");
		
		var entity = repository.findById(book.getId())
		.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		
		
		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel());
		return vo;
	}
	
	
	public void delete(Long id) {
		logger.info("excluindo uma Book");

		var entity = repository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		repository.delete(entity);
	}
	
}
