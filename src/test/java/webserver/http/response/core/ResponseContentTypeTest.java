package webserver.http.response.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.core.RequestPath;
import webserver.http.request.core.RequestPrefixPath;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.*;

class ResponseContentTypeTest {
    private ResponseContentType responseContentType;
    private RequestPath requestPath;

    @Test
    @DisplayName("css type 이 올경우 css content-type 으로 맞게 타입 리턴")
    void CSSType() {
        requestPath = new RequestPath(RequestPrefixPath.of(CSS_PATH), CSS_PATH);
        responseContentType = ResponseContentType.of(requestPath);
        assertThat(responseContentType.getContentType()).isEqualTo(CSS_CONTENT_TYPE);
    }

    @Test
    @DisplayName("js type 이 올경우 js content-type 으로 맞게 타입 리턴")
    void JSType() {
        requestPath = new RequestPath(RequestPrefixPath.of(JS_PATH), JS_PATH);
        responseContentType = ResponseContentType.of(requestPath);
        assertThat(responseContentType.getContentType()).isEqualTo(JS_CONTENT_TYPE);
    }

    @Test
    @DisplayName("fonts type 이 올경우 fonts content-type 으로 맞게 타입 리턴")
    void fontsType() {
        requestPath = new RequestPath(RequestPrefixPath.of(FONT_PATH), FONT_PATH);
        responseContentType = ResponseContentType.of(requestPath);
        assertThat(responseContentType.getContentType()).isEqualTo(FONTS_CONTENT_TYPE);
    }

    @Test
    @DisplayName("images type 이 올경우 images content-type 으로 맞게 타입 리턴")
    void imagesType() {
        requestPath = new RequestPath(RequestPrefixPath.of(IMAGES_PATH), IMAGES_PATH);
        responseContentType = ResponseContentType.of(requestPath);
        assertThat(responseContentType.getContentType()).isEqualTo(IMAGES_CONTENT_TYPE);
    }

    @Test
    @DisplayName("동적 type 이 올경우 동적 content-type 으로 맞게 타입 리턴")
    void dynamicType() {
        requestPath = new RequestPath(RequestPrefixPath.of(GET_PATH), GET_PATH);
        responseContentType = ResponseContentType.of(requestPath);
        assertThat(responseContentType.getContentType()).isEqualTo(DYNAMIC_CONTENT_TYPE);
    }

}