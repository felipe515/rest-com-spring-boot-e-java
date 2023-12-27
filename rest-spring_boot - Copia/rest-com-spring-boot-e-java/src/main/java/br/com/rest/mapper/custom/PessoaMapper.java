package br.com.rest.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.rest.data.vo.v2.PessoaVOV2;
import br.com.rest.model.Pessoa;

@Service
public class PessoaMapper {

	public PessoaVOV2 convertEntityToVO(Pessoa pessoa) {
		PessoaVOV2 vo = new PessoaVOV2();
		vo.setId(pessoa.getId());
		vo.setEndereco(pessoa.getEndereco());
		vo.setDataAniversario(new Date());
		vo.setNome(pessoa.getNome());
		vo.setSobrenome(pessoa.getSobrenome());
		vo.setSexo(pessoa.getSexo());
		return vo;
	}
	
	public Pessoa convertVoTOEntity(PessoaVOV2 pessoa) {
		Pessoa entity = new Pessoa();
		entity.setId(pessoa.getId());
		entity.setEndereco(pessoa.getEndereco());
		//entity.setDataAniversario(new Date());
		entity.setNome(pessoa.getNome());
		entity.setSobrenome(pessoa.getSobrenome());
		entity.setSexo(pessoa.getSexo());
		return entity;
	}
	
}
