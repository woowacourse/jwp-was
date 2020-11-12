package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import utils.IOUtils;

public class HttpRequest {
    private static final String ENTRY_POINT = "/index.html";
    private static final String ROOT = "/";

    private String method;
    private String path;
    private String version;
    private Map<String, String> headers;

    public static HttpRequest of(BufferedReader bufferedReader) throws IOException {
        String s = IOUtils.readData(bufferedReader, 10);
        return of(s);
    }

    public static HttpRequest of(String input) {
        String[] lines = input.split(System.lineSeparator());
        String startLine = lines[0];
        String[] tokens = startLine.split(" ");
        return new HttpRequest(tokens[0], tokens[1], tokens[2],
            Arrays.stream(Arrays.copyOfRange(lines, 1, lines.length))
                .map(line -> line.split(": "))
                .collect(Collectors.toMap(
                    pair -> pair[0], pair -> pair[1]
                )));
    }

    private HttpRequest(String method, String path, String version,
        Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        if (ROOT.equals(path)) {
            return ENTRY_POINT;
        }
        return path;
    }

    public String getVersion() {
        return version;
    }
}
