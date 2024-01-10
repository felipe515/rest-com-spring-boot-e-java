package br.com.rest.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rest.configs.TestConfigs;

import br.com.rest.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.rest.integrationtests.vo.PessoaVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PessoaControllerJsonTest extends AbstractIntegrationTest{

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PessoaVO pessoa;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		pessoa = new PessoaVO();
		
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPessoa();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_REST)
				.setBasePath("/api/pessoa/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification)
					.contentType(TestConfigs.CONTENT_TYPE_jSON)
					.body(pessoa)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body().asString();
		
		PessoaVO persistedPessoa = objectMapper.readValue(content, PessoaVO.class);
		pessoa = persistedPessoa;
		
		assertNotNull(persistedPessoa);
		
		assertNotNull(persistedPessoa.getId());
		assertNotNull(persistedPessoa.getNome());
		assertNotNull(persistedPessoa.getSobrenome());
		assertNotNull(persistedPessoa.getEndereco());
		assertNotNull(persistedPessoa.getSexo());
		
		assertTrue(persistedPessoa.getId() > 0);
		
		assertEquals("Raimundo", persistedPessoa.getNome());
		assertEquals("ferreira", persistedPessoa.getSobrenome());
		assertEquals("canarana", persistedPessoa.getEndereco());
		assertEquals("masculino", persistedPessoa.getSexo());
		
	}
	
	
	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPessoa();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath("/api/pessoa/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification)
					.contentType(TestConfigs.CONTENT_TYPE_jSON)
					.body(pessoa)
					.when()
					.post()
				.then()
					.statusCode(403)
						.extract()
						.body().asString();
		
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
		
	}
	
	
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPessoa();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath("/api/pessoa/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification)
					.contentType(TestConfigs.CONTENT_TYPE_jSON)
					.pathParam("id", pessoa.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body().asString();
		
		PessoaVO persistedPessoa = objectMapper.readValue(content, PessoaVO.class);
		pessoa = persistedPessoa;
		
		assertNotNull(persistedPessoa);
		
		assertNotNull(persistedPessoa.getId());
		assertNotNull(persistedPessoa.getNome());
		assertNotNull(persistedPessoa.getSobrenome());
		assertNotNull(persistedPessoa.getEndereco());
		assertNotNull(persistedPessoa.getSexo());
		
		assertTrue(persistedPessoa.getId() > 0);
		
		assertEquals("Raimundo", persistedPessoa.getNome());
		assertEquals("ferreira", persistedPessoa.getSobrenome());
		assertEquals("canarana", persistedPessoa.getEndereco());
		assertEquals("masculino", persistedPessoa.getSexo());
		
	}
	
	
	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPessoa();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath("/api/pessoa/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification)
					.contentType(TestConfigs.CONTENT_TYPE_jSON)
					.pathParam("id", pessoa.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(403)
						.extract()
						.body().asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
		
		
	}
	
	
	

	private void mockPessoa() {
		pessoa.setNome("Raimundo");
		pessoa.setSobrenome("ferreira");
		pessoa.setEndereco("canarana");
		pessoa.setSexo("masculino");
		
	}

}
