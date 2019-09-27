package controller;

import model.http.HttpRequest;
import model.http.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.HttpStatus;
import utils.RequestDispatcher;
import utils.RequestHeaderParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeControllerTest {
    @Test
    void index_페이지() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/java/data/HttpRequest.txt");
        InputStreamReader inputStream = new InputStreamReader(fileInputStream);
        HttpRequest httpRequest = RequestHeaderParser.parseRequest(inputStream);
        HttpResponse httpResponse = HttpResponse.of();

        RequestDispatcher.handle(httpRequest, httpResponse);
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getPath()).isEqualTo("./templates/index.html");
    }
}
