package http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.jupiter.api.Test;

import http.response.HttpResponse;
import utils.FileIoUtils;

class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse();
        byte[] body = FileIoUtils.loadFileFromClasspath("/test.html");
        ContentType contentType = ContentType.findByURI("/test.html");
        response.setBody(body, contentType);
        response.send(createOutputStream("HTTP_FORWARD.txt"));
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse();
        response.setStatus(HttpStatus.MOVED_PERMANENTLY);
        response.addHeader("Location", "/test.html");
        response.send(createOutputStream("HTTP_REDIRECT.txt"));
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse();
        response.setStatus(HttpStatus.MOVED_PERMANENTLY);
        response.addHeader("Set-Cookie", "logined=true");
        response.addHeader("Location", "/test.html");
        response.send(createOutputStream("HTTP_COOKIE.txt"));
    }

    private DataOutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new DataOutputStream(new FileOutputStream(new File(testDirectory + filename)));
    }
}