package br.com.pbattistella.project.integration_test.controller;

import br.com.pbattistella.project.integration_test.config.TestConfig;
import br.com.pbattistella.project.integration_test.testcontainers.AbstractIntegrationTest;
import br.com.pbattistella.project.model.Customer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Customer customer;
    private static Customer customerTwo;
    private static Customer customerToDelete;
    private static final Date BIRTH_DATE = new Date();

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        customer = new Customer();
        customerTwo = new Customer();
        customerToDelete = new Customer();
    }

    @Test
    @Order(1)
    public void testCreate() throws IOException {
        mockCustomer();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_CUSTOMER_POST)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                            .contentType(TestConfig.CONTENT_TYPE_JSON)
                            .body(customer)
                        .when()
                            .post()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        Customer createdCustomer = objectMapper.readValue(content, Customer.class);

        assertNotNull(createdCustomer.getId());
        assertNotNull(createdCustomer.getEmail());
        assertNotNull(createdCustomer.getPhone());
        assertNotNull(createdCustomer.getGender());
        assertNotNull(createdCustomer.getBirthDate());

        assertTrue(createdCustomer.getId() > 0);

        assertEquals("Pedro da Silva", createdCustomer.getFullName());
        assertEquals("pedro@gmail.com", createdCustomer.getEmail());
        assertEquals("48 9 9898 1234", createdCustomer.getPhone());
        assertEquals("Masculino", createdCustomer.getGender());
        assertEquals(BIRTH_DATE, createdCustomer.getBirthDate());
    }

    @Test
    @Order(2)
    public void testFindById() throws IOException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_CUSTOMER_GET_ID)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                            .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .when()
                            .get()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        customer = objectMapper.readValue(content, Customer.class);

        assertNotNull(customer.getId());
        assertNotNull(customer.getEmail());
        assertNotNull(customer.getPhone());
        assertNotNull(customer.getGender());
        assertNotNull(customer.getBirthDate());

        assertTrue(customer.getId() > 0);

        assertEquals("Pedro da Silva", customer.getFullName());
        assertEquals("pedro@gmail.com", customer.getEmail());
        assertEquals("48 9 9898 1234", customer.getPhone());
        assertEquals("Masculino", customer.getGender());
        assertEquals(BIRTH_DATE, customer.getBirthDate());
    }

    @Test
    @Order(3)
    public void testCreateTwo() throws IOException {
        mockCustomerTwo();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_CUSTOMER_POST)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(customerTwo)
                        .when()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        Customer createdCustomer = objectMapper.readValue(content, Customer.class);

        assertNotNull(createdCustomer.getId());
        assertNotNull(createdCustomer.getFullName());
        assertNotNull(createdCustomer.getEmail());
        assertNotNull(createdCustomer.getPhone());
        assertNotNull(createdCustomer.getGender());
        assertNotNull(createdCustomer.getBirthDate());

        assertTrue(createdCustomer.getId() > 0);

        assertEquals("Maria Jaqueline Santos", createdCustomer.getFullName());
        assertEquals("mjs@gmail.com", createdCustomer.getEmail());
        assertEquals("47 9 9888 4321", createdCustomer.getPhone());
        assertEquals("Feminino", createdCustomer.getGender());
        assertEquals(BIRTH_DATE, createdCustomer.getBirthDate());
    }

    @Test
    @Order(4)
    public void testFindAll() {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_CUSTOMER_GET_ALL)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        assertFalse(content.isEmpty());
    }

    @Test
    @Order(5)
    public void testUpdate() throws IOException {

        customer.setEmail("pedro.silva@gmail.com");
        customer.setPhone("48 9 9981 1234");

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_CUSTOMER_PUT)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(customer)
                        .when()
                        .put()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        Customer createdCustomer = objectMapper.readValue(content, Customer.class);

        assertNotNull(createdCustomer.getId());
        assertNotNull(createdCustomer.getEmail());
        assertNotNull(createdCustomer.getPhone());
        assertNotNull(createdCustomer.getGender());
        assertNotNull(createdCustomer.getBirthDate());

        assertTrue(createdCustomer.getId() > 0);

        assertEquals("Pedro da Silva", createdCustomer.getFullName());
        assertEquals("pedro.silva@gmail.com", createdCustomer.getEmail());
        assertEquals("48 9 9981 1234", createdCustomer.getPhone());
        assertEquals("Masculino", createdCustomer.getGender());
        assertEquals(BIRTH_DATE, createdCustomer.getBirthDate());
    }

    @Test
    @Order(6)
    public void testCreateToDelete() throws IOException {
        mockCustomerToDelete();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_CUSTOMER_POST)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                            .contentType(TestConfig.CONTENT_TYPE_JSON)
                            .body(customerToDelete)
                        .when()
                            .post()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        Customer createdCustomer = objectMapper.readValue(content, Customer.class);

        assertNotNull(createdCustomer.getId());
        assertNotNull(createdCustomer.getEmail());
        assertNotNull(createdCustomer.getPhone());
        assertNotNull(createdCustomer.getGender());
        assertNotNull(createdCustomer.getBirthDate());

        assertTrue(createdCustomer.getId() > 0);

        assertEquals("Donival Santos", createdCustomer.getFullName());
        assertEquals("doni@gmail.com", createdCustomer.getEmail());
        assertEquals("47 9 8897 4433", createdCustomer.getPhone());
        assertEquals("Masculino", createdCustomer.getGender());
        assertEquals(BIRTH_DATE, createdCustomer.getBirthDate());
    }

    private void mockCustomer() {
        customer.setFullName("Pedro da Silva");
        customer.setEmail("pedro@gmail.com");
        customer.setPhone("48 9 9898 1234");
        customer.setGender("Masculino");
        customer.setBirthDate(BIRTH_DATE);
    }

    @Test
    @Order(7)
    public void testDelete() throws IOException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_CUSTOMER_DELETE)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                            .spec(specification)
                            .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .when()
                            .delete()
                        .then()
                            .statusCode(204)
                            .extract()
                            .body()
                            .asString();

        assertTrue(content.isEmpty());
    }

    private void mockCustomerTwo() {
        customerTwo.setFullName("Maria Jaqueline Santos");
        customerTwo.setEmail("mjs@gmail.com");
        customerTwo.setPhone("47 9 9888 4321");
        customerTwo.setGender("Feminino");
        customerTwo.setBirthDate(BIRTH_DATE);
    }

    private void mockCustomerToDelete() {
        customerToDelete.setFullName("Donival Santos");
        customerToDelete.setEmail("doni@gmail.com");
        customerToDelete.setPhone("47 9 8897 4433");
        customerToDelete.setGender("Masculino");
        customerToDelete.setBirthDate(BIRTH_DATE);
    }

}
