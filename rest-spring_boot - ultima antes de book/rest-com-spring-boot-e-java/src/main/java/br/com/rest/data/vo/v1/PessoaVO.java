package br.com.rest.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;



public class PessoaVO implements Serializable{

	private static final long serialVersionUID = 1L;

	
	private Long id;
	private String nome;
	private String sobrenome;
	private String endereco;
	private String sexo;
	
	public PessoaVO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return Objects.hash(endereco, id, nome, sexo, sobrenome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaVO other = (PessoaVO) obj;
		return Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(sexo, other.sexo)
				&& Objects.equals(sobrenome, other.sobrenome);
	}

	
	
}
