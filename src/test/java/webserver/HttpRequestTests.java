package webserver;

import org.junit.jupiter.api.Test;
import webserver.controller.request.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTests {
    public static final String TEST_DIRECTORY = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);
        assertEquals("/index.html", request.getPath());
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);
        assertEquals("/user/create", request.getPath());
    }
}
