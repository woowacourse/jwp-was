package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import utils.IOUtils;
import utils.RequestUtils;

public class HttpRequest {
    public static final String EMPTY = "";
    public static final String REGEX = ":";
    public static final int KEY_INDEX = 0;

    private final String path;
    private final HttpMethod httpMethod;
    private final Map<String, String> header;
    private Map<String, String> parameter;

    public HttpRequest(BufferedReader br) throws IOException {
        this.header = new TreeMap<>();
        String firstLine = readRequest(br);
        this.httpMethod = HttpMethod.valueOf(RequestUtils.extractMethod(firstLine));
        this.path = RequestUtils.extractPath(firstLine);
        createParameterIfPost(br);
    }

    private String readRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        createHeader(br);

        return line;
    }

    private void createHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!EMPTY.equals(line) && line != null) {
            String key = line.split(REGEX)[KEY_INDEX];
            String value = line.substring(key.length() + 2);
            header.put(key, value);
            line = br.readLine();
        }
    }

    private void createParameterIfPost(BufferedReader br) throws IOException {
        if (HttpMethod.POST == httpMethod) {
            String body = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
            this.parameter = RequestUtils.extractParameter(body);
        }
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }

    public Map<String, String> getParameter() {
        return parameter;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
