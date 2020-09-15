package web.server.domain.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpResponseTest {

    private static final String INDEX_PAGE = "/index.html";
    private String testDirectory = "./src/test/java/resources/";

    @DisplayName("정적 파일을 요청했을 때 HttpResponse에 Status Code 200과 정적파일 내용이 담기는지 확인한다.")
    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("http_forward.txt"));
        response.forward(INDEX_PAGE);
        String actual = readFile("http_forward.txt");
        assertAll(
            () -> assertThat(actual).contains("200"),
            () -> assertThat(actual).contains("</div>")
        );
    }

    @DisplayName("다른 URL로 리다이렉트 요청이 왔을 때 HttpResponse에 Status Code 302와 해당 Location 정보가 담기는지 확인한다.")
    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("http_redirect.txt"));
        response.sendRedirect(INDEX_PAGE);
        String actual = readFile("http_redirect.txt");
        assertAll(
            () -> assertThat(actual).contains("302"),
            () -> assertThat(actual).contains("Location: " + INDEX_PAGE)
        );
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

    private String readFile(String path) throws IOException {
        File file = new File(testDirectory + path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
