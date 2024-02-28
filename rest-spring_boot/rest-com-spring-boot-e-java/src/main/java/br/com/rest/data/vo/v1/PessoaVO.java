package br.com.rest.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;


@JsonPropertyOrder({"Id", "nome", "sobrenome", "endereco", "sexo"})
public class PessoaVO extends RepresentationModel<PessoaVO> implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("Id") //nome que vai aparecer no postman
	@Mapping("id")
	private Long key;
	
	//@JsonProperty("primeiro_nome")
	private String nome;
	private String sobrenome;
	
	//@JsonIgnore //para ocultar no postman
	private String endereco;
	private String sexo;
	
	public PessoaVO() {
		
	}

	

	public Long getKey() {
		return key;
	}



	public void setKey(Long key) {
		this.key = key;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(endereco, key, nome, sexo, sobrenome);
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaVO other = (PessoaVO) obj;
		return Objects.equals(endereco, other.endereco) && Objects.equals(key, other.key)
				&& Objects.equals(nome, other.nome) && Objects.equals(sexo, other.sexo)
				&& Objects.equals(sobrenome, other.sobrenome);
	}

	

	
	
}
