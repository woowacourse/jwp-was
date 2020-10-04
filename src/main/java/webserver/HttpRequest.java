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

    private final HttpMethod httpMethod;
    private final String path;
    private final String model;
    private final Map<String, String> header;
    private final Map<String, String> parameter;

    public HttpRequest(BufferedReader br) throws IOException {
        this.header = new TreeMap<>();
        String firstLine = readRequest(br);
        this.httpMethod = HttpMethod.valueOf(RequestUtils.extractMethod(firstLine));
        this.model = RequestUtils.extractTitleOfModel(firstLine);
        this.path = RequestUtils.extractPath(firstLine);
        this.parameter = createParameter(br);
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

    private Map<String, String> createParameter(BufferedReader br) throws IOException {
        if (header.containsKey("Content-Length")) {
            String body = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
            return RequestUtils.extractParameter(body);
        }
        return new TreeMap<>();
    }

    public boolean isGet() {
        return httpMethod.isGet();
    }

    public boolean isPost() {
        return httpMethod.isPost();
    }

    public boolean isPut() {
        return httpMethod.isPut();
    }

    public boolean isDelete() {
        return httpMethod.isDelete();
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

    public String getModel() {
        return model;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
