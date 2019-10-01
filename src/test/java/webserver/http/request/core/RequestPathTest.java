package webserver.http.request.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.*;

class RequestPathTest {
    private RequestPrefixPath requestPrefixPath;
    private RequestPath requestPath;

    @Test
    void CSS_path() {
        requestPrefixPath = RequestPrefixPath.of(CSS_PATH);
        requestPath = new RequestPath(requestPrefixPath, CSS_PATH);
        assertThat(requestPath.getFullPath()).isEqualTo(STATIC_PREFIX_PATH + CSS_PATH);
    }

    @Test
    void JS_path() {
        requestPrefixPath = RequestPrefixPath.of(JS_PATH);
        requestPath = new RequestPath(requestPrefixPath, JS_PATH);
        assertThat(requestPath.getFullPath()).isEqualTo(STATIC_PREFIX_PATH + JS_PATH);
    }

    @Test
    void Images_path() {
        requestPrefixPath = RequestPrefixPath.of(IMAGES_PATH);
        requestPath = new RequestPath(requestPrefixPath, IMAGES_PATH);
        assertThat(requestPath.getFullPath()).isEqualTo(STATIC_PREFIX_PATH + IMAGES_PATH);
    }

    @Test
    void Fonts_path() {
        requestPrefixPath = RequestPrefixPath.of(FONT_PATH);
        requestPath = new RequestPath(requestPrefixPath, FONT_PATH);
        assertThat(requestPath.getFullPath()).isEqualTo(STATIC_PREFIX_PATH + FONT_PATH);
    }

    @Test
    void Dynamic_path() {
        requestPrefixPath = RequestPrefixPath.of(GET_PATH);
        requestPath = new RequestPath(requestPrefixPath, GET_PATH);
        assertThat(requestPath.getFullPath()).isEqualTo(DYNAMIC_PREFIX_PATH + GET_PATH);
    }
}