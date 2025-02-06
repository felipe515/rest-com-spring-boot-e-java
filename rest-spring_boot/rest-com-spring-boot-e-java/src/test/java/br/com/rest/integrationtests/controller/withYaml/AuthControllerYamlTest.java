package br.com.rest.integrationtests.controller.withYaml;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.rest.configs.TestConfigs;
import br.com.rest.integrationtests.controller.withYaml.mapper.YMLMapper;
import br.com.rest.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.rest.integrationtests.vo.AccountCredentialsVO;
import br.com.rest.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest{
	
	private static YMLMapper objectmapper;
	private TokenVO tokenVO;
	
	@BeforeAll
	public static void setup() {
		objectmapper = new YMLMapper();
	}

	@Test
	@Order(1)
	public void testeSginin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "1234");
		
		//var accessToken = given().
				 tokenVO = given()
						 .config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
						  .accept(TestConfigs.CONTENT_TYPE_YML)
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YML)
				.body(user, objectmapper)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class, objectmapper);
							//.getAccessToken();
		
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testeRefresh() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "1234");
		
		RequestSpecification specification = new RequestSpecBuilder()
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var newTokenVO = given().spec(specification)
				 .config(
						  RestAssuredConfig
						  .config()
						  .encoderConfig(EncoderConfig.encoderConfig()
						  .encodeContentTypeAs(
						  TestConfigs.CONTENT_TYPE_YML, 
						  ContentType.TEXT)))
						  .accept(TestConfigs.CONTENT_TYPE_YML)
				.basePath("/auth/refresh")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YML)
				.pathParam("username", tokenVO.getUsername())
				.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer" + tokenVO.getRefreshToken())
					.when()
				.put("{username}")
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class, objectmapper);
		
		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
}
