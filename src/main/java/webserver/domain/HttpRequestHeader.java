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

    public static final String NEW_LINE = "\n";
    public static final String SPACE = " ";
    public static final String QUESTION_MARK = "?";
    public static final String COLON = ": ";

    private final RequestMethod requestMethod;
    private final String path;
    private final Map<String, String> queryParams;
    private final Map<String, String> headerParams;

    public HttpRequestHeader(String header) throws UnsupportedEncodingException {
        String[] lines = header.split(NEW_LINE);
        String firstLineOfHeader = lines[0];
        this.requestMethod = RequestMethod.from(firstLineOfHeader.split(SPACE)[0]);

        this.queryParams = new HashMap<>();
        String url = firstLineOfHeader.split(SPACE)[1];
        int delimiterIndex = url.indexOf(QUESTION_MARK);
        if (delimiterIndex != -1) {
            this.path = url.substring(0, delimiterIndex);
            String query = url.substring(delimiterIndex + 1);
            IOUtils.addParameters(query, this.queryParams);
        } else {
            this.path = url;
        }

        this.headerParams = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(COLON);
            headerParams.put(keyValue[0], keyValue[1]);
        }
    }

    public boolean isStaticFile() {
        return Pattern.matches("^.*[.][a-z]+$", this.path);
    }

    public boolean hasEqualPathWith(String target) {
        return this.path.equals(target);
    }

    public int findContentLength() {
        String length = this.getHeaderParams()
            .getOrDefault("Content-Length", "0");
        return Integer.parseInt(length);
    }

    public StaticFileType findExtension() {
        String[] split = this.path.split("\\.");
        String extensionName = split[split.length - 1];
        return StaticFileType.valueOf(extensionName.toUpperCase());
    }
}
