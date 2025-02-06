package br.com.rest.integrationtests.controller.withXml;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.rest.configs.TestConfigs;
import br.com.rest.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.rest.integrationtests.vo.AccountCredentialsVO;
import br.com.rest.integrationtests.vo.TokenVO;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest{
	private TokenVO tokenVO;
	

	@Test
	@Order(1)
	public void testeSginin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "1234");
		
		//var accessToken = given().
				 tokenVO = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_XML)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class);
							//.getAccessToken();
		
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testeRefresh() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "1234");
		
		var newTokenVO = given()
				.basePath("/auth/refresh")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("username", tokenVO.getUsername())
				.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer" + tokenVO.getRefreshToken())
					.when()
				.put("{username}")
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class);
		
		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
}
