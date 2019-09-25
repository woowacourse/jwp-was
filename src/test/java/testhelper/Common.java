package testhelper;

import http.HttpRequest;
import http.HttpRequestBody;
import http.HttpRequestHeader;
import utils.IOUtils;

import java.io.*;
import java.util.List;

public class Common {
    private static final String TEST_DIRECTORY_PATH = "./src/test/resources/";

    public static HttpRequest parsePostHttpRequest(String path) throws IOException {
        String absolutePath = TEST_DIRECTORY_PATH + path;

        InputStream inputStream = new FileInputStream(new File(absolutePath));
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        List<String> inputs = IOUtils.parseHeader(bufferedReader);
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(inputs);

        String body = IOUtils.readData(bufferedReader, httpRequestHeader);
        HttpRequestBody httpRequestBody = new HttpRequestBody(body);

        return new HttpRequest(httpRequestHeader, httpRequestBody);
    }
}
