package br.com.rest.integrationtests.controller.withYaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.rest.configs.TestConfigs;
import br.com.rest.integrationtests.controller.withYaml.mapper.YMLMapper;
import br.com.rest.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.rest.integrationtests.vo.AccountCredentialsVO;
import br.com.rest.integrationtests.vo.PessoaVO;
import br.com.rest.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PessoaControllerYamlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static YMLMapper objectMapper;

	private static PessoaVO person;
	
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(PessoaControllerJsonTest.class);
	
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();	
		person = new PessoaVO();
	}
	
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "1234");
		
		var accessToken = given()
				.config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.body(user, objectMapper)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class, objectMapper)
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
		
		var persistedPerson = given().spec(specification)
				.config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PessoaVO.class, objectMapper);
		

		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getSexo());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Nelson", persistedPerson.getNome());
		assertEquals("Stallman", persistedPerson.getSobrenome());
		assertEquals("New York City, New York, US", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getSexo());
		
	}

	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setSobrenome("Andrade");
		
		var persistedPerson = given().spec(specification)
				.config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PessoaVO.class, objectMapper);
		

		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getSexo());
		
		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getNome());
		assertEquals("Andrade", persistedPerson.getSobrenome());
		assertEquals("New York City, New York, US", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getSexo());
		
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var persistedPerson = given().spec(specification)
				.config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PessoaVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getSexo());
		
		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getNome());
		assertEquals("Stallman", persistedPerson.getSobrenome());
		assertEquals("New York City, New York, US", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getSexo());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
	
				given().spec(specification)
				.config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("id", person.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PessoaVO[].class, objectMapper);
		
		List<PessoaVO> people = Arrays.asList(content);
		
		PessoaVO foundPersonOne = people.get(0);

		assertNotNull(foundPersonOne);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getNome());
		assertNotNull(foundPersonOne.getSobrenome());
		assertNotNull(foundPersonOne.getEndereco());
		assertNotNull(foundPersonOne.getSexo());
		
		assertEquals(1, foundPersonOne.getId());
		
		assertEquals("Nelson", foundPersonOne.getNome());
		assertEquals("Stallman", foundPersonOne.getSobrenome());
		assertEquals("New York City, New York, US", foundPersonOne.getEndereco());
		assertEquals("Male", foundPersonOne.getSexo());
		
		PessoaVO foundPersonSix = people.get(5);

		assertNotNull(foundPersonSix.getId());
		assertNotNull(foundPersonSix.getNome());
		assertNotNull(foundPersonSix.getSobrenome());
		assertNotNull(foundPersonSix.getEndereco());
		assertNotNull(foundPersonSix.getSexo());
		
		assertEquals(9, foundPersonSix.getId());
		
		assertEquals("Nelson", foundPersonSix.getNome());
		assertEquals("Mvezo", foundPersonSix.getSobrenome());
		assertEquals("Mvezo - South Africa", foundPersonSix.getEndereco());
		assertEquals("Male", foundPersonSix.getSexo());
		
	}
	
	private void mockPerson() {
		person.setNome("Nelson");
		person.setSobrenome("Stallman");
		person.setEndereco("New York City, New York, US");
		person.setSexo("Male");
	}
}
		