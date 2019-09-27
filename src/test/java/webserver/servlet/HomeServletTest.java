package webserver.servlet;

import helper.IOHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

class HomeServletTest {
    @DisplayName("루트로 get요청")
    @Test
    void doGet_rootGetRequest_ok() throws IOException, URISyntaxException {
        BufferedReader bufferedReader = new BufferedReader(IOHelper.createBuffer(
                "GET / HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html,*/*"
        ));

    }
}