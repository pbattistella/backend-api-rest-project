package br.com.pbattistella.project.integration_test.controller;

import br.com.pbattistella.project.integration_test.config.TestConfig;
import br.com.pbattistella.project.integration_test.testcontainers.AbstractIntegrationTest;
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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Project project;
    private static Project projectToDelete;
    private static final Date START_DATE = new Date();
    private static final Date END_DATE = new Date();

    private static Customer manager;
    private static final Date BIRTH_DATE = new Date();

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        project = new Project();
        projectToDelete = new Project();

        manager = new Customer();
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

        assertEquals("João Maria Silva", manager.getFullName());
        assertEquals("joao@gmail.com", manager.getEmail());
        assertEquals("48 9 8988 1133", manager.getPhone());
        assertEquals("Masculino", manager.getGender());
        assertEquals(BIRTH_DATE, manager.getBirthDate());
    }

    @Test
    @Order(2)
    public void testCreate() throws IOException {
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

        var createdProject = objectMapper.readValue(content, Project.class);

        assertNotNull(createdProject.getId());
        assertNotNull(createdProject.getName());
        assertNotNull(createdProject.getDescription());
        assertNotNull(createdProject.getStartDate());
        assertNotNull(createdProject.getEndDate());
        assertNotNull(createdProject.getStatus());

        assertTrue(createdProject.getId() > 0);

        assertEquals("Projeto 1", createdProject.getName());
        assertEquals("Um projeto a ser implementado", createdProject.getDescription());
        assertEquals(START_DATE, createdProject.getStartDate());
        assertEquals(END_DATE, createdProject.getEndDate());
        assertEquals(manager, createdProject.getManager());
    }

    @Test
    @Order(3)
    public void testFindById() throws IOException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_PROJECT_GET_ID)
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
                            .get()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        project = objectMapper.readValue(content, Project.class);

        assertEquals("Projeto 1", project.getName());
        assertEquals("Um projeto a ser implementado", project.getDescription());
        assertEquals(START_DATE, project.getStartDate());
        assertEquals(END_DATE, project.getEndDate());
        assertEquals(manager, project.getManager());
    }

    @Test
    @Order(4)
    public void testUpdate() throws IOException {

        project.setName("Um novo projeto 1");
        project.setDescription("Descrição de um novo projeto");
        project.setStatus(StatusEnum.OPEN);

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_PROJECT_PUT)
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
                            .put()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        project = objectMapper.readValue(content, Project.class);

        assertEquals("Um novo projeto 1", project.getName());
        assertEquals("Descrição de um novo projeto", project.getDescription());
        assertEquals(StatusEnum.OPEN, project.getStatus());
        assertEquals(START_DATE, project.getStartDate());
        assertEquals(END_DATE, project.getEndDate());
        assertEquals(manager, project.getManager());
    }

    @Test
    @Order(5)
    public void testCreateToDelete() throws IOException {
        mockProjectToDelete();
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
                            .body(projectToDelete)
                        .when()
                            .post()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        var createdProject = objectMapper.readValue(content, Project.class);

        assertNotNull(createdProject.getId());
        assertNotNull(createdProject.getName());
        assertNotNull(createdProject.getDescription());
        assertNotNull(createdProject.getStartDate());
        assertNotNull(createdProject.getEndDate());
        assertNotNull(createdProject.getStatus());

        assertTrue(createdProject.getId() > 0);
    }

    @Test
    @Order(6)
    public void testFindAll() {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_PROJECT_GET_ALL)
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
    @Order(7)
    public void testDelete() throws IOException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .setBasePath(TestConfig.URL_PROJECT_DELETE)
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

    private void mockManager() {
        manager.setFullName("João Maria Silva");
        manager.setEmail("joao@gmail.com");
        manager.setPhone("48 9 8988 1133");
        manager.setGender("Masculino");
        manager.setBirthDate(BIRTH_DATE);
    }

    private void mockProject() {
        project.setName("Projeto 1");
        project.setDescription("Um projeto a ser implementado");
        project.setStartDate(START_DATE);
        project.setEndDate(END_DATE);
        project.setStatus(StatusEnum.IN_PROGRESS);
        project.setManager(manager);
    }

    private void mockProjectToDelete() {
        projectToDelete.setName("Projeto para deletar");
        projectToDelete.setDescription("Projeto para ser deletado");
        projectToDelete.setStartDate(START_DATE);
        projectToDelete.setEndDate(END_DATE);
        projectToDelete.setStatus(StatusEnum.CANCELLED);
        projectToDelete.setManager(manager);
    }

}
