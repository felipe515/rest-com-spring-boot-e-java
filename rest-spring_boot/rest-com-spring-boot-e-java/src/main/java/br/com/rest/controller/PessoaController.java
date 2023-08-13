package br.com.rest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rest.data.vo.v1.PessoaVO;
import br.com.rest.service.PessoaServices;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

		@Autowired
		private PessoaServices service;
		
		
		@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		public List<PessoaVO> findAll(){	
			return service.findAll();
		}
		
		
		@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
		public PessoaVO findById(@PathVariable(value = "id") Long id){	
			return service.findById(id);
		}
		
		@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		public PessoaVO criar(@RequestBody PessoaVO pessoa){	
			return service.criar(pessoa);
		}
		
		
		@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		public PessoaVO atualizar(@RequestBody PessoaVO pessoa){	
			return service.atualizar(pessoa);
		}
		
		@DeleteMapping(value = "/{id}")
		public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){	
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
		
}
