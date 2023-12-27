package br.com.rest.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rest.data.vo.v1.PessoaVO;
import br.com.rest.data.vo.v2.PessoaVOV2;
import br.com.rest.exception.ResourceNotFoundException;
import br.com.rest.mapper.DozerMapper;
import br.com.rest.mapper.custom.PessoaMapper;
import br.com.rest.model.Pessoa;
import br.com.rest.repositories.PessoaRepository;

@Service
public class PessoaServices {
	
	private Logger logger = Logger.getLogger(PessoaServices.class.getName());
	
	@Autowired
	PessoaRepository repository;
	
	@Autowired
	PessoaMapper mapper;
	
	public List<PessoaVO> findAll() {
		logger.info("todas as pessoas");
	
		return DozerMapper.parseListObject(repository.findAll(), PessoaVO.class);
	}
	


	public PessoaVO findById(Long id) {
		logger.info("Encontrando uma Pessoa!");
		
		var entity = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		return DozerMapper.parseObject(entity, PessoaVO.class);
	}

	
	
	public PessoaVO criar(PessoaVO pessoa) {
		logger.info("criando uma pessoa");
		var entity = DozerMapper.parseObject(pessoa, Pessoa.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PessoaVO.class);
		return vo;
	}
	
	public PessoaVOV2 criarV2(PessoaVOV2 pessoa) {
		logger.info("criando uma pessoa");
		var entity = mapper.convertVoTOEntity(pessoa);
		var vo = mapper.convertEntityToVO(repository.save(entity));
		return vo;
	}
	
	
	public PessoaVO atualizar(PessoaVO pessoa) {
		logger.info("atualizando uma pessoa");
		
		var entity = repository.findById(pessoa.getId())
		.orElseThrow(()-> new ResourceNotFoundException("nenhum registro encontrado para esse ID"));
		
		
		entity.setNome(pessoa.getNome());
		entity.setSobrenome(pessoa.getSobrenome());
		entity.setEndereco(pessoa.getEndereco());
		entity.setSexo(pessoa.getSexo());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PessoaVO.class);
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

