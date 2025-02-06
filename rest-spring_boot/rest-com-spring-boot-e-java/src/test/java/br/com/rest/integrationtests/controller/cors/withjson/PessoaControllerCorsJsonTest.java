package br.com.rest.integrationtests.controller.cors.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import br.com.rest.integrationtests.vo.AccountCredentialsVO;
import br.com.rest.integrationtests.vo.PessoaVO;
import br.com.rest.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PessoaControllerCorsJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PessoaVO person;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PessoaControllerCorsJsonTest.class);
	
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	
		person = new PessoaVO();
	}
	
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "1234");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/pessoa/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.body(person)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PessoaVO persistedPerson = objectMapper.readValue(content, PessoaVO.class);

		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getSexo());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Richard", persistedPerson.getNome());
		assertEquals("Stallman", persistedPerson.getSobrenome());
		assertEquals("New York City, New York, US", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getSexo());
		

	}

	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
					.body(person)
				.when()
					.post()
				.then()
					.statusCode(403)
						.extract()
							.body()
								.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PessoaVO persistedPerson = objectMapper.readValue(content, PessoaVO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getSexo());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Richard", persistedPerson.getNome());
		assertEquals("Stallman", persistedPerson.getSobrenome());
		assertEquals("New York City, New York, US", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getSexo());
	}
	

	
	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)

					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(403)
						.extract()
						.body()
							.asString();

		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
 
	
	private void mockPerson() {
		person.setNome("Richard");
		person.setSobrenome("Stallman");
		person.setEndereco("New York City, New York, US");
		person.setSexo("Male");
	}
}
		