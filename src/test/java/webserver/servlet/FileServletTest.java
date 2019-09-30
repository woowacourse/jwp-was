package webserver.servlet;

import org.junit.jupiter.api.Test;
import utils.HttpRequestUtils;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.FileView;
import webserver.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class FileServletTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void run () throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_index_test.txt"));
        HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        HttpResponse httpResponse = new HttpResponse(null);
        FileServlet fileServlet = new FileServlet();
        View fileView = fileServlet.run(httpRequest, httpResponse);
        String filePath = HttpRequestUtils.generateTemplateFilePath(httpRequest.getAbsPath());
        assertThat(fileView).isEqualTo(new FileView(filePath));
    }
}