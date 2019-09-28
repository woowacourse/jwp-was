package webserver.servlet;

import helper.IOHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.parser.HttpRequestParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

class FileServletTest {
    @DisplayName("정적 html파일 가져오기")
    @Test
    void run_httpFileRequest_ok() throws IOException {
//        BufferedReader br = IOHelper.createBuffer(
//                "GET /index.html HTTP/1.1",
//                "Host: localhost:8080",
//                "Connection: keep-alive",
//                "Accept: text/html,*/*"
//        );
//        FileServlet fileServlet =  new FileServlet();
//        HttpRequest httpRequest = HttpRequestParser.parse(br);
//        HttpResponse httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
    }
}