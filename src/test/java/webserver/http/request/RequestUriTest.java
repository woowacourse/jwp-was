package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestUriTest {
    RequestUri requestUri;
    RequestUri queryRequestUri;

    @BeforeEach
    void setUp() {
        requestUri = new RequestUri("/abc/cdf");
        queryRequestUri = new RequestUri("/abc/cdf?id=woowa");

    }

    @DisplayName("쿼리 있는 객체 생성")
    @Test
    void create_query_notThrow() {
        assertDoesNotThrow(() -> {
                    new RequestUri("/abc/cdf?id=woowa");
                }
        );
    }

    @DisplayName("쿼리 없는 객체 생성")
    @Test
    void create_noQuery_notThrow() {
        assertDoesNotThrow(() -> {
                    new RequestUri("/abc");
                }
        );
    }

    @DisplayName("쿼리 유무 관계 없이 같은 절대경로 얻기")
    @Test
    void getAbsPath_all_same() {
        String expected = "/abc/cdf";
        assertThat(requestUri.getAbsPath()).isEqualTo(expected);
        assertThat(queryRequestUri.getAbsPath()).isEqualTo(expected);
    }

    @DisplayName("쿼리 얻기")
    @Test
    void getQueryString(){
        assertThat(queryRequestUri.getQueryString("id")).isEqualTo("woowa");
    }
}