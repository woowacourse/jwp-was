package utils;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;

import java.io.*;

public class TestResourceLoader {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    public static HttpRequest getHttpRequest(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + fileName));
        return HttpRequestFactory.getHttpRequest(in);
    }
}
