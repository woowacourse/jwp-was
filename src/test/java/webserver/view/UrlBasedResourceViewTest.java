package webserver.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UrlBasedResourceViewTest {

    @Test
    @DisplayName("입력된 뷰에 해당하는 Body가 포함된 헤더를 잘 생성하는지 테스트")
    void urlBasedHeader() {
        Response response = new Response();
        View view = new UrlBasedResourceView("/user/login.html");
        Map<String, Object> models = new HashMap<>();

        view.render(response, models);

        String actual = new String(response.toBytes());
        assertThat(actual).contains("200 OK");
        assertThat(actual).contains("<button type=\"submit\" class=\"btn btn-success clearfix pull-right\">로그인</button>");
    }

}