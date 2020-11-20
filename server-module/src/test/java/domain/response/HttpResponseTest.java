package domain.response;

import static common.IoUtil.createOutputStream;
import static common.IoUtil.readFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.StaticFileType;

public class HttpResponseTest {

    private static final String INDEX_PAGE = "./templates/index.html";

    @DisplayName("정적 파일을 요청했을 때 HttpResponse에 Status Code 200과 정적파일 내용이 담기는지 확인한다.")
    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("/out/http_forward.txt"));
        response.forward(INDEX_PAGE, StaticFileType.HTML);
        String actual = readFile("/out/http_forward.txt");
        assertAll(
            () -> assertThat(actual).contains("200"),
            () -> assertThat(actual).contains("</div>")
        );
    }

    @DisplayName("다른 URL로 리다이렉트 요청이 왔을 때 HttpResponse에 Status Code 302와 해당 Location 정보가 담기는지 확인한다.")
    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("/out/http_redirect.txt"));
        response.sendRedirect(INDEX_PAGE);
        String actual = readFile("/out/http_redirect.txt");
        assertAll(
            () -> assertThat(actual).contains("302"),
            () -> assertThat(actual).contains("Location: " + INDEX_PAGE)
        );
    }

    @DisplayName("없는 경로로 요청이 왔을 때 HttpResponse에 Status Code 404가 담기는지 확인한다.")
    @Test
    public void responsePageNotFound() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("/out/http_url_not_found.txt"));
        response.respondPageNotFound();
        String actual = readFile("/out/http_url_not_found.txt");
        assertThat(actual).contains("404");
    }

    @Test
    void respondMethodNotAllowed() throws IOException {
        HttpResponse response = new HttpResponse(createOutputStream("/out/method_not_allowed.txt"));
        response.respondMethodNotAllowed();
        String actual = readFile("/out/method_not_allowed.txt");
        assertThat(actual).contains("405");
    }
}
