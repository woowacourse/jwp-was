package webserver.servlet;

import helper.IOHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.HttpRequestUtils.generateTemplateFilePath;

class HomeServletTest {
    @DisplayName("루트로 get요청")
    @Test
    void doGet_rootGetRequest_ok() throws IOException, URISyntaxException {
        BufferedReader bufferedReader = new BufferedReader(IOHelper.createBuffer(
                "GET / HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html,*/*"

        ));
        HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);
        HomeServlet homeServlet = new HomeServlet();
        String filePath = generateTemplateFilePath(httpRequest.getAbsPath() + "index.html");
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
        assertThat(homeServlet.doGet(httpRequest)).isEqualTo(new HttpResponse(HttpStatus.OK, header, body));
    }
}