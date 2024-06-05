package br.com.pbattistella.project.integration_test.controller;

import br.com.pbattistella.project.dto.ActivityDTO;
import br.com.pbattistella.project.integration_test.config.TestConfig;
import br.com.pbattistella.project.integration_test.testcontainers.AbstractIntegrationTest;
import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Customer;
import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.util.StatusEnum;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActivityControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Project project;
    private static final Date START_DATE = new Date();
    private static final Date END_DATE = new Date();

    private static Customer manager;
    private static Customer customer;
    private static final Date BIRTH_DATE = new Date();

    private static ActivityDTO activityDTO;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        project = new Project();
        manager = new Customer();
        customer = new Customer();
        activityDTO = new ActivityDTO();
    }

    @Test
    @Order(1)
    public void testCreateManager() throws IOException {
        mockManager();
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
                            .body(manager)
                        .when()
                            .post()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        manager = objectMapper.readValue(content, Customer.class);

        assertNotNull(manager.getId());
        assertNotNull(manager.getEmail());
        assertNotNull(manager.getPhone());
        assertNotNull(manager.getGender());
        assertNotNull(manager.getBirthDate());

        assertTrue(manager.getId() > 0);

        assertEquals("Juliana Fernandes", manager.getFullName());
        assertEquals("juliana@gmail.com", manager.getEmail());
        assertEquals("48 9 9899 2211", manager.getPhone());
        assertEquals("Feminino", manager.getGender());
        assertEquals(BIRTH_DATE, manager.getBirthDate());
    }

    @Test
    @Order(2)
    public void testCreateProject() throws IOException {
        mockProject();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_PROJECT_POST)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(project)
                        .when()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        project = objectMapper.readValue(content, Project.class);

        assertNotNull(project.getId());
        assertNotNull(project.getName());
        assertNotNull(project.getDescription());
        assertNotNull(project.getStartDate());
        assertNotNull(project.getEndDate());
        assertNotNull(project.getStatus());
    }

    @Test
    @Order(3)
    public void testCreateCustomer() throws IOException {
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

        customer = objectMapper.readValue(content, Customer.class);

        assertNotNull(customer.getId());
        assertNotNull(customer.getEmail());
        assertNotNull(customer.getPhone());
        assertNotNull(customer.getGender());
        assertNotNull(customer.getBirthDate());

        assertTrue(customer.getId() > 0);
    }

    @Test
    @Order(4)
    public void testCreateActivity() throws IOException {
        mockActivity();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_ACTIVITY_POST)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                            .spec(specification)
                            .contentType(TestConfig.CONTENT_TYPE_JSON)
                            .body(activityDTO)
                        .when()
                            .post()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        var createdActivity = objectMapper.readValue(content, Activity.class);
        activityDTO.setId(createdActivity.getId());

        assertNotNull(createdActivity.getId());
        assertNotNull(createdActivity.getName());
        assertNotNull(createdActivity.getDescription());
        assertNotNull(createdActivity.getStartDate());
        assertNotNull(createdActivity.getEndDate());

        assertTrue(createdActivity.getId() > 0);

        assertEquals("Uma atividade", createdActivity.getName());
        assertEquals("Descrição da atividade", createdActivity.getDescription());
        assertEquals(START_DATE, createdActivity.getStartDate());
        assertEquals(END_DATE, createdActivity.getEndDate());
    }

    @Test
    @Order(5)
    public void testUpdateActivity() throws IOException {
        activityDTO.setName("Alterando nome da atividade");
        activityDTO.setDescription("Alterando descrição da atividade");
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_ACTIVITY_PUT)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                            .spec(specification)
                            .contentType(TestConfig.CONTENT_TYPE_JSON)
                            .body(activityDTO)
                        .when()
                            .put()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        var createdActivity = objectMapper.readValue(content, Activity.class);
        assertNotNull(createdActivity.getId());
        assertNotNull(createdActivity.getName());
        assertNotNull(createdActivity.getDescription());

        assertTrue(createdActivity.getId() > 0);

        assertEquals("Alterando nome da atividade", createdActivity.getName());
        assertEquals("Alterando descrição da atividade", createdActivity.getDescription());
    }

    @Test
    @Order(6)
    public void testFindActivity() throws IOException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_ACTIVITY_GET_PROJECT)
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

    private void mockManager() {
        manager.setFullName("Juliana Fernandes");
        manager.setEmail("juliana@gmail.com");
        manager.setPhone("48 9 9899 2211");
        manager.setGender("Feminino");
        manager.setBirthDate(BIRTH_DATE);
    }

    private void mockProject() {
        project.setName("Projeto com Atividade");
        project.setDescription("Um projeto com atividade");
        project.setStartDate(START_DATE);
        project.setEndDate(END_DATE);
        project.setStatus(StatusEnum.IN_PROGRESS);
        project.setManager(manager);
    }

    private void mockCustomer() {
        customer.setFullName("Roberto Amaral");
        customer.setEmail("roberto@gmail.com");
        customer.setPhone("48 9 9999 6655");
        customer.setGender("Masculino");
        customer.setBirthDate(BIRTH_DATE);
    }

    private void mockActivity() {
        activityDTO.setProject(project.getId());
        activityDTO.setCustomer(customer.getId());
        activityDTO.setName("Uma atividade");
        activityDTO.setDescription("Descrição da atividade");
        activityDTO.setStartDate(START_DATE);
        activityDTO.setEndDate(END_DATE);
    }

}
