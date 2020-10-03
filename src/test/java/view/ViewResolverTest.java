package view;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.HttpStatus;
import http.StaticFile;
import http.response.HttpResponse;

class ViewResolverTest {

    @DisplayName("Redirect 테스트")
    @Test
    void redirect() throws IOException, URISyntaxException {
        ModelAndView modelAndView = ModelAndView.from(new View("redirect:/index.html"));

        ViewResolver viewResolver = new ViewResolver();
        HttpResponse httpResponse = viewResolver.resolve(modelAndView);

        assertAll(
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(httpResponse.getHeader("Location")).isEqualTo("/index.html")
        );
    }

    @DisplayName("응답 200 OK 테스트")
    @Test
    void response_200_OK() throws IOException, URISyntaxException {
        ModelAndView modelAndView = ModelAndView.from(new View("/index.html"));

        ViewResolver viewResolver = new ViewResolver();
        HttpResponse httpResponse = viewResolver.resolve(modelAndView);

        assertAll(
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK),
            () -> assertThat(httpResponse.getBody()).isNotEmpty(),
            () -> assertThat(httpResponse.getHeaders()).isNotNull(),
            () -> assertThat(httpResponse.getHeader("Content-Type")).isEqualTo(StaticFile.HTML.getContentType()),
            () -> assertThat(httpResponse.getHeader("Content-Length")).isNotEmpty()
        );

    }
}
