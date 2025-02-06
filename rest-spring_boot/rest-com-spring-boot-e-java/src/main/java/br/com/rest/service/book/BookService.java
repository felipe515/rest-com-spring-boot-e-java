package br.com.rest.service.book;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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
	
	@Autowired
	PagedResourcesAssembler<BookVO> assembler;

	/*
	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

		logger.info("Finding all books!");

		var booksPage = repository.findAll(pageable);

		var booksVOs = booksPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
		booksVOs.map(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		
		Link findAllLink = linkTo(
		          methodOn(BookController.class)
		          	.findAll(pageable.getPageNumber(),
	                         pageable.getPageSize(),
	                         "asc")).withSelfRel();
		
		return assembler.toModel(booksVOs, findAllLink);
	}

	*/
	

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
