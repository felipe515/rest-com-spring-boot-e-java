package br.com.rest.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.rest.controller.PessoaController;
import br.com.rest.data.vo.v1.PessoaVO;
import br.com.rest.exception.RequiredObjectIsNullFoundException;
import br.com.rest.exception.ResourceNotFoundException;
import br.com.rest.mapper.DozerMapper;
import br.com.rest.model.Pessoa;
import br.com.rest.repositories.PessoaRepository;

@Service
public class PessoaServices {
	
	private Logger logger = Logger.getLogger(PessoaServices.class.getName());
	
	@Autowired
	PessoaRepository repository;

	
	public List<PessoaVO> findAll() {
		logger.info("todas as pessoas");
	
		var persons = DozerMapper.parseListObject(repository.findAll(), PessoaVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PessoaController.class).findById(p.getKey())).withSelfRel()));
		
		return persons;
	}
	


	public PessoaVO findById(Long id) {
		logger.info("Encontrando uma Pessoa!");
		
		var entity = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		var vo = DozerMapper.parseObject(entity, PessoaVO.class);
		vo.add(linkTo(methodOn(PessoaController.class).findById(id)).withSelfRel());
		return vo;
	}

	
	
	public PessoaVO criar(PessoaVO pessoa) {
		
		if(pessoa == null) throw new RequiredObjectIsNullFoundException();
		logger.info("criando uma pessoa");
		var entity = DozerMapper.parseObject(pessoa, Pessoa.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PessoaVO.class);
		vo.add(linkTo(methodOn(PessoaController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	
	public PessoaVO atualizar(PessoaVO pessoa) {
		
		if(pessoa == null) throw new RequiredObjectIsNullFoundException();
		
		logger.info("atualizando uma pessoa");
		
		var entity = repository.findById(pessoa.getKey())
		.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		
		
		entity.setNome(pessoa.getNome());
		entity.setSobrenome(pessoa.getSobrenome());
		entity.setEndereco(pessoa.getEndereco());
		entity.setSexo(pessoa.getSexo());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PessoaVO.class);
		vo.add(linkTo(methodOn(PessoaController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	
	public void delete(Long id) {
		logger.info("excluindo uma pessoa");

		var entity = repository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		repository.delete(entity);
	}
	
	
/*
	private Pessoa mockPessoa(int i) {

		Pessoa pessoa = new Pessoa();
		pessoa.setId(counter.incrementAndGet());
		pessoa.setNome("Pessoa nome " + i);
		pessoa.setSobrenome("Sobrenome " + i);
		pessoa.setEndereco("Algum endereço do Brasil " + i);
		pessoa.setSexo("Masculino");
		return pessoa;
	}
	*/
}

