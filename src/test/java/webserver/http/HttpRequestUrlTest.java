package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import webserver.http.exception.InvalidHttpRequestException;
import webserver.http.request.HttpRequestUrl;

class HttpRequestUrlTest {

    @DisplayName("url에서 path만 반환한다.")
    @CsvSource(value = {"/user/form.html,/user/form.html", "/user/form.html?name=toney,/user/form.html"})
    @ParameterizedTest
    void getPath(String inputUrl, String expected) {
        HttpRequestUrl httpRequestUrl = new HttpRequestUrl(inputUrl);

        assertThat(expected).isEqualTo(httpRequestUrl.getPath());
    }

    @DisplayName("파라미터가 올바르지 않을 경우 예외처리한다.")
    @ValueSource(strings = {
        "/user/form.html?=no-key&age=12",
        "/user/form.html?duplicated-key=toney&duplicated-key=parky"
    })
    @ParameterizedTest
    public void getParametersThrowExceptionWhenInvalidUrl(String invalidUrl) {
        assertThatThrownBy(() -> new HttpRequestUrl(invalidUrl)).isInstanceOf(InvalidHttpRequestException.class);
    }

    @DisplayName("url에서 파라미터를 추출하여 반환한다.")
    @CsvSource(value = {"/user/form.html?name=toney,name,toney"})
    @ParameterizedTest
    void getParameters(String input, String key, String value) {
        HttpRequestUrl httpRequestUrl = new HttpRequestUrl(input);

        assertThat(key).isIn(httpRequestUrl.getParameters().keySet());
        assertThat(value).isEqualTo(httpRequestUrl.getParameters().get(key));
    }

    @DisplayName("파라미터가 있더라도 동일한 path라면 true를 반환한다.")
    @CsvSource(value = {"/user/form.html?name=toney,/user/form.html"})
    @ParameterizedTest
    void isSamePath(String pathWithParameter, String path) {
        HttpRequestUrl urlWithParameter = new HttpRequestUrl(pathWithParameter);
        HttpRequestUrl url = new HttpRequestUrl(path);

        assertTrue(url.isSamePath(urlWithParameter.getPath()));
    }
}