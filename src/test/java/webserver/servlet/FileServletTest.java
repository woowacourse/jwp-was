package webserver.servlet;

import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileServletTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void run () throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_index_test.txt"));
        HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        FileServlet fileServlet = new FileServlet();
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getFilePath());
        HttpResponse httpResponse = fileServlet.run(httpRequest);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(httpRequest.getFilePath()));
        assertThat(httpResponse).isEqualTo(new HttpResponse(HttpStatus.OK,header, body));
    }
}