package web.server.domain.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import web.server.utils.StaticFileType;

@Getter
public class RequestLine {

    private static final Pattern STATIC_FILE_PATTERN = Pattern.compile("^.*[.][a-z]+$");
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String EXTENSION_DELIMITER = "\\.";

    private final RequestMethod requestMethod;
    private final RequestUri requestUri;

    public RequestLine(String requestLine) {
        String[] requestLineParameters = requestLine.split(REQUEST_LINE_DELIMITER);
        this.requestMethod = RequestMethod.from(requestLineParameters[0]);
        this.requestUri = new RequestUri(requestLineParameters[1]);
    }

    public boolean hasPathOfStaticFile() {
        Matcher matcher = STATIC_FILE_PATTERN.matcher(getPath());
        return matcher.matches();
    }

    public boolean hasEqualPathWith(String target) {
        return getPath().equals(target);
    }

    public StaticFileType findExtension() {
        String[] split = getPath().split(EXTENSION_DELIMITER);
        String extensionName = split[split.length - 1];
        return StaticFileType.from(extensionName);
    }

    public String getPath() {
        return requestUri.getPath();
    }

    public String getQuery() {
        return requestUri.getQuery();
    }

    public boolean isPostMethod() {
        return this.requestMethod == RequestMethod.POST;
    }
}
