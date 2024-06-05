package br.com.pbattistella.project.integration_test.config;

public class TestConfig {
    public static final int SERVER_PORT = 8080;
    public static final String HEADER_PARAM_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_PARAM_ACCEPT = "Accept";
    public static final String HEADER_PARAM_ACCEPT_ALL = "*/*";

    public static final String URL_CUSTOMER_POST = "/api/customer/";
    public static final String URL_CUSTOMER_PUT = "/api/customer/1";
    public static final String URL_CUSTOMER_GET_ID = "/api/customer/1";
    public static final String URL_CUSTOMER_GET_ALL = "/api/customer/";
    public static final String URL_CUSTOMER_DELETE = "/api/customer/3";
}
