package webserver.servlet;

import helper.IOHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.parser.HttpRequestParser;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.HttpRequestUtils.generateTemplateFilePath;

class FileServletTest {
    @DisplayName("정적 html파일 가져오기")
    @Test
    void run_httpFileRequest_ok() throws IOException, URISyntaxException {
        BufferedReader bufferedReader = IOHelper.createBuffer(
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html,*/*"
        );
        HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);
        FileServlet fileServlet = new FileServlet();
        String filePath = generateTemplateFilePath( "/index.html");
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        HttpResponse httpResponse = fileServlet.run(httpRequest);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
        assertThat(httpResponse).isEqualTo(new HttpResponse(HttpStatus.OK, header, body));
    }
}