package util;

import java.util.ArrayList;
import webserver.dto.Headers;
import webserver.dto.Parameters;
import webserver.dto.Protocol;
import webserver.dto.UrlPath;

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

    public static final Protocol PROTOCOL = Protocol.of("HTTP/1.1");
    public static final Parameters PARAMETERS_EMPTY = Parameters.fromEncodedParameter("");
    public static final Parameters PARAMETERS_FOR_CREATE_USER = Parameters.fromEncodedParameter(
        USER_ID + "=" + USER_ID
            + "&" + USER_EMAIL + "=" + USER_EMAIL
            + "&" + USER_PASSWORD + "=" + USER_PASSWORD
            + "&" + USER_NAME + "=" + USER_NAME
    );
    public static final Headers HEADERS_EMPTY = Headers.from(new ArrayList<>());

    public static final String EMPTY = "";
}
