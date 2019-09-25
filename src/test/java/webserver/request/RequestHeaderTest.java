package webserver.request;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {
    @Test
    void create_headerMap_true(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "59");
        headers.put("Content-Type", "text/css");

        RequestHeader requestHeader = new RequestHeader(headers);
        assertThat(requestHeader.getHeader("Content-Length")).isEqualTo("59");
    }

    @Test
    void getCookie_logined_true(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-e98840a1=365d551c-5d0c-4192-923c-a0070fb208db; logined=true");

        RequestHeader requestHeader = new RequestHeader(headers);
        assertThat(requestHeader.getCookie("logined")).isEqualTo("true");
        assertThat(requestHeader.getCookie("Idea-e98840a1")).isEqualTo("365d551c-5d0c-4192-923c-a0070fb208db");
    }
}