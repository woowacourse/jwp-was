package http.response;

import http.request.HttpRequest;
import http.request.core.RequestPath;
import http.request.core.RequestPrefixPath;
import http.response.core.Response;
import http.response.core.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.*;

class StaticResponseTest {
    private RequestPath requestPath;
    private List<Object> firstLineTokens;
    private HttpRequest httpRequest;

    @Test
    @DisplayName("CSS 정적 파일을 response body 에 맞는 데이터를 담는 테스트")
    void CssResponseTest() throws IOException, URISyntaxException {
        requestPath = new RequestPath(RequestPrefixPath.of(CSS_PATH), CSS_PATH);

        firstLineTokens = Arrays.asList(GET_METHOD, requestPath, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        Response response = new StaticResponseCreator().create(httpRequest);
        ResponseBody responseBody = response.doResponse();

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(OK_CSS_BODY);
    }

    @Test
    @DisplayName("Js 정적 파일을 response body 에 맞는 데이터를 담는 테스트")
    void JsResponseTest() throws IOException, URISyntaxException {
        requestPath = new RequestPath(RequestPrefixPath.of(JS_PATH), JS_PATH);

        firstLineTokens = Arrays.asList(GET_METHOD, requestPath, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        Response response = new StaticResponseCreator().create(httpRequest);
        ResponseBody responseBody = response.doResponse();

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(OK_JS_BODY);
    }

    @Test
    @DisplayName("Fonts 정적 파일을 response body 에 맞는 데이터를 담는 테스트")
    void FontsResponseTest() throws IOException, URISyntaxException {
        requestPath = new RequestPath(RequestPrefixPath.of(FONT_PATH), FONT_PATH);

        firstLineTokens = Arrays.asList(GET_METHOD, requestPath, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        Response response = new StaticResponseCreator().create(httpRequest);
        ResponseBody responseBody = response.doResponse();

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(OK_FONTS_BODY);
    }

    @Test
    @DisplayName("Images 정적 파일을 response body 에 맞는 데이터를 담는 테스트")
    void ImagesResponseTest() throws IOException, URISyntaxException {
        requestPath = new RequestPath(RequestPrefixPath.of(IMAGES_PATH), IMAGES_PATH);

        firstLineTokens = Arrays.asList(GET_METHOD, requestPath, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        Response response = new StaticResponseCreator().create(httpRequest);
        ResponseBody responseBody = response.doResponse();

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(OK_IMAGES_BODY);
    }
}