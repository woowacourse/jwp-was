package http.controller;

import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class StaticFileControllerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";
    private static final String TEMPLATES_DIRECTORY = "./templates/";

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    public void doGetTest() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET_File.txt"));
        byte[] contents = FileIoUtils.loadFileFromClasspath(TEMPLATES_DIRECTORY + "index.html");
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
        httpResponse = new ResponseHandler().create(httpRequest);

        StaticFileController staticFileController = new StaticFileController();
        staticFileController.doGet(httpRequest, httpResponse);

        System.out.println(httpResponse.toString());
        assertThat(httpResponse.toString()).contains("200 OK");
        assertThat(httpResponse.toString()).contains("Content-Length: " + contents.length);
        assertThat(httpResponse.toString()).contains("Content-Type: text/html;charset=utf-8");
    }
}