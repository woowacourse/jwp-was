package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import utils.StaticFileType;

@Getter
public class RequestLine {

    private static final Pattern staticFilePattern = Pattern.compile("^.*[.][a-z]+$");
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String URI_DELIMITER_REGEX = "\\?";
    private static final String URI_DELIMITER = "?";
    private static final String EXTENSION_DELIMITER = "\\.";

    private final RequestMethod requestMethod;
    private final String path;
    private final RequestParams requestParams;


    public RequestLine(String requestLine) throws UnsupportedEncodingException {
        String[] requestLineParameters = requestLine.split(REQUEST_LINE_DELIMITER);
        this.requestMethod = RequestMethod.from(requestLineParameters[0]);

        String requestURI = requestLineParameters[1];
        String[] pathQuerySplit = requestURI.split(URI_DELIMITER_REGEX);
        this.path = pathQuerySplit[0];
        String query = "";
        if (hasQueryString(pathQuerySplit.length)) {
            int delimiterIndex = requestURI.indexOf(URI_DELIMITER);
            query = requestURI.substring(delimiterIndex + 1);
        }
        this.requestParams = new RequestParams(query);
    }

    private boolean hasQueryString(int pathQuerySplitSize) {
        return pathQuerySplitSize >= 2;
    }

    public boolean isStaticFile() {
        Matcher matcher = staticFilePattern.matcher(this.path);
        return matcher.matches();
    }

    public boolean hasEqualPathWith(String target) {
        return this.path.equals(target);
    }

    public StaticFileType findExtension() {
        String[] split = this.path.split(EXTENSION_DELIMITER);
        String extensionName = split[split.length - 1];
        return StaticFileType.valueOf(extensionName.toUpperCase());
    }

}
