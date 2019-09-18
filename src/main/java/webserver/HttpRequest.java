package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final Map<String, String> headerContents = new HashMap<>();
    private String body;

    private BufferedReader bufferedReader;

    public HttpRequest(InputStream in) {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            createHeader();
            createBody();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void createBody() throws IOException {
        if (headerContents.containsKey("Content-Length")) {
            body = IOUtils.readData(bufferedReader, Integer.parseInt(headerContents.get("Content-Length")));
            logger.info("request body contents: {}", body);
        }
    }

    private void createHeader() throws IOException {
        String line = bufferedReader.readLine();
        String[] tokens = line.split(" ");
        System.out.println(line);
        headerContents.put(HttpRequestCoreInfo.METHOD.name(), tokens[0]);
        createAddress(tokens[1]);

        headerContents.put(HttpRequestCoreInfo.HTTP_VERSION.name(), tokens[2]);

        while (true) {
            line = bufferedReader.readLine();
            logger.info("request header contents: {}", line);
            if (line == null || line.equals("")) {
                break;
            }
            String[] keyValue = line.split(": ");
            headerContents.put(keyValue[0], keyValue[1]);
        }
    }

    private void createAddress(String token) {
        String[] address = token.split("\\?");
        if (address.length > 1) {
            headerContents.put(HttpRequestCoreInfo.QUERY_STRING.name(), address[1]);
        }
        headerContents.put(HttpRequestCoreInfo.PATH.name(), address[0]);
    }

    public RequestMethod getMethod() {
        return RequestMethod.valueOf(headerContents.get(HttpRequestCoreInfo.METHOD.name()));
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    public String getPath() {
        return headerContents.get(HttpRequestCoreInfo.PATH.name());
    }

    public String getQueryString() {
        try {
            return URLDecoder.decode(headerContents.get(HttpRequestCoreInfo.QUERY_STRING.name()), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported");
        }
    }

    public String getBody() {
        try {
            return URLDecoder.decode(body, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported");
        }
    }
}
