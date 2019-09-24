package utils;

import http.request.core.RequestHeader;

import java.util.Arrays;

public class StringUtils {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String GET_PATH = "/index.html";
    public static final String GET_PARAMS_PATH = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    public static final String POST_BODY = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    public static final String VERSION = "HTTP/1.1";

    public static final String LINE = "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Accept: */*";

    public static final String FIRST_GET_0_9_LINE = "GET /index.html HTTP/0.9";
    public static final String FIRST_POST_1_0_LINE = "POST /user/create HTTP/1.0";
    public static final String FIRST_PUT_1_1_LINE = "PUT /user/change HTTP/1.1";
    public static final String FIRST_DELETE_2_0_LINE = "DELETE /user/delete HTTP/2.0";
    public static final String FIRST_WRONG_2_1_LINE = "PET /user/create HTTP/2.1";

    public static final String STATIC_PREFIX_PATH = "../resources/static";
    public static final String DYNAMIC_PREFIX_PATH = "../resources/templates";

    public static final String CSS_PATH = "/css/style.css";
    public static final String JS_PATH = "/js/script.js";
    public static final String IMAGES_PATH = "/images/80-text.png";
    public static final String FONT_PATH = "/fonts/glyphicons-halflings-regular.eot";

    public static final String CSS_CONTENT_TYPE = "Content-Type: text/css;charset=utf-8\r\n";
    public static final String JS_CONTENT_TYPE = "Content-Type: application/javascript;charset=utf-8\r\n";
    public static final String FONTS_CONTENT_TYPE = "Content-Type: font/opentype;charset=utf-8\r\n";
    public static final String IMAGES_CONTENT_TYPE = "Content-Type: image/jpeg\r\n";
    public static final String DYNAMIC_CONTENT_TYPE = "Content-Type: text/html;charset=utf-8\r\n";

    public static final RequestHeader GET_REQUEST_HEADER = new RequestHeader(Arrays.asList(
            "Host: localhost:8080 \n",
            "Connection: keep-alive \n",
            "Accept: */*"
    ));

    public static final RequestHeader POST_REQUEST_HEADER = new RequestHeader(Arrays.asList(
            "Host: localhost:8080 \n",
            "Connection: keep-alive \n",
            "Content-Length: 46 \n",
            "Content-Type: application/x-www-form-urlencoded \n",
            "Accept: */*"
    ));

    public static final String OK_BODY = "HTTP/1.1 200 OK \r\n" +
            "Content-Type: text/html;charset=utf-8\r\n" +
            "Content-Length: 6902\r\n\r\n";

    public static final String FOUND_BODY = "HTTP/1.1 302 Found \r\n" +
            "Location: http://localhost:8080/\r\n";

}
