package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import utils.IOUtils;
import utils.RequestUtils;

public class HttpRequest {
    public static final String EMPTY = "";
    public static final String COLON = ":";
    public static final int KEY_INDEX = 0;
    public static final String QUERY_REGEX = "\\?";
    public static final String EQUALS = "=";
    public static final int PARAMETER_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    private final HttpMethod httpMethod;
    private final String model;
    private final Map<String, String> header;
    private final Map<String, String> parameter;
    private String path;

    public HttpRequest(BufferedReader br) throws IOException {
        this.header = new TreeMap<>();
        String firstLine = readRequest(br);
        this.httpMethod = HttpMethod.valueOf(RequestUtils.extractMethod(firstLine));
        this.model = RequestUtils.extractTitleOfModel(firstLine);
        this.path = RequestUtils.extractPath(firstLine);
        this.parameter = createParameter(br);
        extractQueryParam();
    }

    private void extractQueryParam() {
        String[] queries = path.split(QUERY_REGEX);
        path = queries[0];
        for (int i = 1; i < queries.length; i++) {
            String[] split = queries[i].split(EQUALS);
            parameter.put(split[PARAMETER_INDEX], split[VALUE_INDEX]);
        }
    }

    private String readRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        createHeader(br);

        return line;
    }

    private void createHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!EMPTY.equals(line) && line != null) {
            String key = line.split(COLON)[KEY_INDEX];
            String value = line.substring(key.length() + 2);
            header.put(key, value);
            line = br.readLine();
        }
    }

    private Map<String, String> createParameter(BufferedReader br) throws IOException {
        if (header.containsKey(HttpHeader.CONTENT_LENGTH)) {
            String body = IOUtils.readData(br, Integer.parseInt(header.get(HttpHeader.CONTENT_LENGTH)));
            return RequestUtils.extractParameter(body);
        }
        return new TreeMap<>();
    }

    public String getSessionId() {
        String cookie = header.get(HttpHeader.COOKIE);
        if (cookie == null) {
            return null;
        }
        return RequestUtils.extractSessionId(cookie);
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

    public boolean containsAll(String... keys) {
        return parameter.keySet()
            .containsAll(Arrays.asList(keys.clone()));
    }

    public boolean containsParameter(String key) {
        return parameter.containsKey(key);
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
