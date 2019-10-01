package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";
    private HttpRequest request;

    @BeforeEach
    void setUp() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        request = new HttpRequest(in);
    }

    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(request, createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(request, createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(request, createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
