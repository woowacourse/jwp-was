package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lombok.Getter;
import utils.IOUtils;
import utils.StaticFileType;

@Getter
public class HttpRequestHeader {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "?";
    private static final String COLON = ": ";

    private final RequestLine requestLine;
    private final Map<String, String> headerParams;

    public HttpRequestHeader(String header) throws UnsupportedEncodingException {
        String[] lines = header.split(NEW_LINE);
        String requestLine = lines[0];
        this.requestLine = new RequestLine(requestLine);

        this.headerParams = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(COLON);
            headerParams.put(keyValue[0], keyValue[1]);
        }
    }

    public boolean isStaticFile() {
        return this.requestLine.isStaticFile();
    }

    public boolean hasEqualPathWith(String target) {
        return this.requestLine.hasEqualPathWith(target);
    }

    public StaticFileType findExtension() {
        return this.requestLine.findExtension();
    }

    public int findContentLength() {
        String length = this.getHeaderParams()
            .getOrDefault("Content-Length", "0");
        return Integer.parseInt(length);
    }

    public RequestMethod getRequestMethod() {
        return requestLine.getRequestMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }
}
