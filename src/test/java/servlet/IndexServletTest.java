package servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.WebServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class IndexServletTest {
    private HttpTestClient httpTestClient;

    @BeforeEach
    void setUp() throws IOException {
        httpTestClient = new HttpTestClient(WebServer.DEFAULT_PORT);
    }

    @Test
    void get() {
        final String response = httpTestClient.get().uri("/")
                .exchange();

        assertThat(response).contains("runtime 에");
    }
}