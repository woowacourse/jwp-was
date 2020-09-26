package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import httpmethod.HttpMethod;
import httpmethod.Method;
import model.Model;
import utils.RequestUtils;

public class HttpRequest {
    public static final String EMPTY = "";
    public static final String REGEX = ":";
    public static final int KEY_INDEX = 0;

    private final String path;
    private final Method method;
    private final Map<String, String> header;
    private final Map<String, String> parameter;

    public HttpRequest(BufferedReader br) throws IOException {
        this.header = new TreeMap<>();
        String firstLine = readRequest(br);
        this.method = HttpMethod.valueOf(RequestUtils.extractMethod(firstLine))
            .getMethod();
        this.path = RequestUtils.extractPath(firstLine);
        this.parameter = method.extractParameter(br, header);
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

    public Model createModel() {
        return method.extractModel(path, parameter);
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

    public Method getMethod() {
        return method;
    }

    public String getMethodName() {
        return method.getName();
    }
}
