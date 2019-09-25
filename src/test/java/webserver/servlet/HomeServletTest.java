package webserver.servlet;

import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.HttpRequestUtils.generateTemplateFilePath;

class HomeServletTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void doGet() throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_root_test.txt"));
        HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        HomeServlet homeServlet = new HomeServlet();
        String filePath = generateTemplateFilePath(httpRequest.getAbsPath() + "index.html");
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        ResponseHeader header = new ResponseHeader();
        header.setContentLegthAndType(body.length, FileIoUtils.loadMIMEFromClasspath(filePath));
        assertThat(homeServlet.doGet(httpRequest)).isEqualTo(new HttpResponse(HttpStatus.OK, header, body));
    }
}