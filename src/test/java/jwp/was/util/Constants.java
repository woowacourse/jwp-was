package jwp.was.util;

import java.util.ArrayList;
import jwp.was.webserver.dto.Headers;
import jwp.was.webserver.dto.HttpVersion;
import jwp.was.webserver.dto.Parameters;
import jwp.was.webserver.dto.UrlPath;

public class Constants {

    public static final String USER_ID = "userId";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";

    public static final String USER_OTHER_ID = "otherUserId";
    public static final String USER_OTHER_PASSWORD = "otherPassword";
    public static final String USER_OTHER_NAME = "otherName";
    public static final String USER_OTHER_EMAIL = "otherEmail";

    public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain;charset=utf-8";
    public static final String CONTENT_TYPE_TEXT_CSS = "text/css;charset=utf-8";
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=utf-8";


    public static final UrlPath URL_PATH_NOT_EXISTS_FILE = UrlPath.from("/wrong.file");
    public static final UrlPath URL_PATH_INDEX_HTML = UrlPath.from("/index.html");
    public static final UrlPath URL_PATH_BOOTSTRAP_MIN_CSS = UrlPath.from("/css/bootstrap.min.css");
    public static final UrlPath URL_PATH_API_CREATE_USER = UrlPath.from("/user/create");
    public static final UrlPath URL_PATH_PAGE_API_USER_LIST = UrlPath.from("/user/list");

    public static final HttpVersion HTTP_VERSION = HttpVersion.of("HTTP/1.1");
    public static final Parameters PARAMETERS_EMPTY = Parameters.fromEncodedParameter("");
    public static final Headers HEADERS_EMPTY = Headers.from(new ArrayList<>());
    public static final String EMPTY = "";

    private static final String LOGIN_PARAMETER
        = USER_ID + "=" + USER_ID + "&" + USER_PASSWORD + "=" + USER_PASSWORD;
    public static final Parameters PARAMETERS_FOR_LOGIN
        = Parameters.fromEncodedParameter(LOGIN_PARAMETER);
    public static final Parameters PARAMETERS_FOR_CREATE_USER = Parameters.fromEncodedParameter(
        LOGIN_PARAMETER + "&" + USER_EMAIL + "=" + USER_EMAIL + "&" + USER_NAME + "=" + USER_NAME);
}
