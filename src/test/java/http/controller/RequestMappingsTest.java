package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestMappingsTest extends BaseControllerTest {
    @Test
    void 파라미터에_중복_있음() {
        assertThatThrownBy(() ->
                new RequestMappings(RequestMapping.GET("/index.html"), RequestMapping.GET("/index.html")))
                .isInstanceOf(IllegalRequestMappingException.class);
    }

    @Test
    void 파라미터_없음() {
        assertThatThrownBy(() ->
                new RequestMappings())
                .isInstanceOf(IllegalRequestMappingException.class);
    }

    @Test
    void 정상_생성후_리퀘스트매핑_존재_확인() {
        RequestMappings mappings = new RequestMappings(RequestMapping.GET("/index.html"), RequestMapping.POST("/user/create"));
        ServletRequest getRequest = getDefaultRequest(HttpMethod.GET, "/index.html").build();
        ServletRequest postRequest = getDefaultRequest(HttpMethod.POST, "/user/create").build();

        assertThat(mappings.hasMapping(getRequest)).isTrue();
        assertThat(mappings.hasMapping(postRequest)).isTrue();
    }
}