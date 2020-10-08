package http.response;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.request.RequestParams;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "text/html");
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.GET, "/index.html"),
                new RequestHeader(header),
                new RequestParams(new HashMap<>())
        );
        response.forward(httpRequest);
    }

    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseRedirectWhenLogin() throws Exception {
        // Http_Redirect_When_Login.txt 결과는 응답 headere에 Location 정보와 Set-Cookie 정보가 포하되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect_When_Login.txt"));
        response.putHeader(HttpResponse.SET_COOKIE, "logined=true; path");
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseNotAllowed() throws Exception {
        // Http_Redirect_Not_Allowed.txt 결과는 응답 body에 <h1>405 Try another method</h1> 문구가 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect_Not_Allowed.txt"));
        response.methodNotAllowed();
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
