package dto;

import exception.NotExistsUrlPath;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UrlPath {

    private static final String URL_PATH_DELIMITER = "?";
    private static final String URL_PATH_DELIMITER_REGEX = "\\" + URL_PATH_DELIMITER;
    private static final int URL_PATH_INDEX = 0;
    private static final String EXTENSION_DELIMITER = ".";

    private final String urlPath;

    private UrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public static UrlPath from(String encodedUrl) {
        String decodedUrl = decodeUTF8(encodedUrl);
        String decodedUrlPath = getUrlPathFromUrl(decodedUrl);
        return new UrlPath(decodedUrlPath);
    }

    private static String decodeUTF8(String encodedUrl) {
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);
    }

    private static String getUrlPathFromUrl(String url) {
        String[] urlPathAndQueryStrings = getUrlPathAndQueryStrings(url);
        return urlPathAndQueryStrings[URL_PATH_INDEX];
    }

    static String[] getUrlPathAndQueryStrings(String url) {
        validateExistsUrlPath(url);
        return url.split(URL_PATH_DELIMITER_REGEX);
    }

    private static void validateExistsUrlPath(String encodedUrl) {
        if (encodedUrl.startsWith(URL_PATH_DELIMITER)) {
            throw new NotExistsUrlPath("[" + encodedUrl + "] URL PATH가 존재하지 않습니다.");
        }
    }

    public String getUrlPath() {
        return urlPath;
    }

    public boolean isFile() {
        return urlPath.contains(EXTENSION_DELIMITER);
    }
}
