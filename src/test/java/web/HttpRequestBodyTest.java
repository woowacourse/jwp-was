package web;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.http.HttpRequestBody;
import web.http.HttpRequestLine;

class HttpRequestBodyTest {

    @DisplayName("RequestBody 생성")
    @Test
    public void create() throws IOException {

        String input = "userId=javajigi&password=javable&name=pobi&email=test@test.com";

        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequestBody httpRequestBody = HttpRequestBody.of(br, String.valueOf(input.length()), HttpRequestLine.from("POST /user/create HTTP/1.1"));

        assertThat(httpRequestBody.getParams()).containsEntry("userId", "javajigi");
        assertThat(httpRequestBody.getParams()).containsEntry("password", "javable");
        assertThat(httpRequestBody.getParams()).containsEntry("name", "pobi");
        assertThat(httpRequestBody.getParams()).containsEntry("email", "test@test.com");
    }

}