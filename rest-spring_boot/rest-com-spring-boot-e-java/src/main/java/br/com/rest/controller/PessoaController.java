package br.com.rest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rest.data.vo.v1.PessoaVO;
import br.com.rest.service.PessoaServices;
import br.com.rest.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/api/pessoa/v1")
@Tag(name = "Pessoa", description = "Endpoints para administrar pessoas")
public class PessoaController {

		@Autowired
		private PessoaServices service;
		
		
		@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
		@Operation(summary = "listar todas as pessoas", description = "listar todas as pessoas", tags = {"Pessoa"},
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
					}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
		)
		public ResponseEntity<PagedModel<EntityModel<PessoaVO>>> findAll(
				@RequestParam(value = "page", defaultValue = "0") Integer page, 
				@RequestParam(value = "size", defaultValue = "12") Integer size,
				@RequestParam(value = "direction", defaultValue = "asc") String direction
				){
			
			var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
			
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
			return ResponseEntity.ok(service.findAll(pageable));
		}
		
		
		
		@GetMapping(value = "/findPersonByName/{nome}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
		@Operation(summary = "encontrar pessoas pelo nome", description = "encontrar pessoas pelo nome", tags = {"Pessoa"},
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
					}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
		)
		public ResponseEntity<PagedModel<EntityModel<PessoaVO>>> findPersonsByName(
				@PathVariable(value = "nome") String firstName, 
				@RequestParam(value = "page", defaultValue = "0") Integer page, 
				@RequestParam(value = "size", defaultValue = "12") Integer size,
				@RequestParam(value = "direction", defaultValue = "asc") String direction
				){
			
			var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
			
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
			return ResponseEntity.ok(service.findPersonsByName("nome", pageable));
		}
		
		
		
		@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
		@Operation(summary = "listando uma pessoa", description = "listando uma pessoa", tags = {"Pessoa"},
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
					}),
				@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
		)
		public PessoaVO findById(@PathVariable(value = "id") Long id){	
			return service.findById(id);
		}
		
		@PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
		@Operation(summary = "Adicionar uma nova pessoa", description = "Adicionar uma nova pessoa", tags = {"Pessoa"},
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
					}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
		)
		public PessoaVO criar(@RequestBody PessoaVO pessoa){	
			return service.criar(pessoa);
		}
		
		
		@PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
		@Operation(summary = "Atualizar uma pessoa", description = "atualizar uma nova pessoa", tags = {"Pessoa"},
		responses = {
				@ApiResponse(description = "Atualizado", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
					}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
		)
		public PessoaVO atualizar(@RequestBody PessoaVO pessoa){	
			return service.atualizar(pessoa);
		}
		
		@PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
		@Operation(summary = "Disabled a specific Person by your ID", description = "Disabled a specific Person by your ID", tags = {"Pessoa"},
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PessoaVO.class))
									
					),
				@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
		)
		public PessoaVO disabledPessoa(@PathVariable(value = "id") Long id){	
			return service.disablePerson(id);
		}
		
		
		
		@DeleteMapping(value = "/{id}")
		@Operation(summary = "Deletar uma pessoa", description = "Deletar uma pessoa", tags = {"Pessoa"},
		responses = {
				@ApiResponse(description = "No content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
		)
		public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){	
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
		
}
