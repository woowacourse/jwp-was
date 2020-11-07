package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import utils.StringUtils;

public class Request {
    public static final String INDEX_FILE = "/";
    public static final String INDEX_HTML = "/index.html";
    public static final String TEMPLATE_PREFIX = "./templates";
    public static final String STATIC_PREFIX = "./static";
    public static final String HTML_SUFFIX = ".html";
    public static final String ICO_SUFFIX = ".ico";

    private final String request;

    public Request(InputStream inputStream) throws IOException {
        if (Objects.isNull(inputStream)) {
            this.request = "";
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        this.request = parse(bufferedReader);
    }

    private String parse(BufferedReader bufferedReader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (Objects.nonNull(line) && !line.isEmpty()) {
            builder.append(line).append(System.lineSeparator());
            line = bufferedReader.readLine();
        }
        return builder.toString();
    }

    public String getPath() {
        String filename = StringUtils.getFilename(request);
        if (INDEX_FILE.equals(filename)) {
            return TEMPLATE_PREFIX + INDEX_HTML;
        }
        if (filename.endsWith(HTML_SUFFIX) || filename.endsWith(ICO_SUFFIX)) {
            return TEMPLATE_PREFIX + filename;
        }
        return STATIC_PREFIX + filename;
    }

    @Override
    public String toString() {
        return request;
    }
}
