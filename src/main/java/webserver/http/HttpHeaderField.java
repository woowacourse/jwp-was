package webserver.http;

import webserver.http.exception.NoMatchHeaderFieldException;

import java.util.Arrays;

public enum HttpHeaderField {
    //General Header
    CACHE_CONTROL("Cache-Control"), CONNECTION("Connection"), DATE("Date"),
    PRAGMA("Pragma"), TRAILER("Trailer"), TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"), VIA("Via"), WARNING("Warning"),

    //Request Header
    ACCEPT("Accept"), ACCEPT_CHARSET("Accept-Charset"), ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept_Language"), AUTHORIZATION("Authorization"), EXPECT("Expect"), FORM("Form"),
    HOST("Host"), IF_MATCH("If-Match"), IF_MODIFIED_SINCE("If-Modified-Since"), IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"), IF_UNMODIFIED_SINCE("If-Unmodified-Since"), MAX_FORWARDS("Max-Forwards"),
    PROXY_AUTHORIZATION("Proxy-Authorization"), RANGE("Range"), REFERER("Referer"),
    TE("TE"), USER_AGENT("User-Agent"), COOKIE("Cookie"), ORIGIN("Origin"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"), SEC_FETCH_SITE("Sec-Fetch-Site"), SEC_FETCH_USER("Sec-Fetch-User"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),

    //Entity Header
    ALLOW("Allow"), CONTENT_ENCODING("Content-Encoding"), CONTENT_LANGUAGE("Content-Language"), CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"), CONTENT_MD5("Content-MD5"), CONTENT_RANGE("Content-Range"), CONTENT_TYPE("Content-Type"),
    EXPIRES("Expires"), LAST_MODIFIED("Last-Modified"), EXTENSION_HEADER("Extension-header"),

    //Response Header
    ACCEPT_RANGES("Accept-Ranges"), AGE("Age"), ETAG("ETag"), LOCATION("Location"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"), RETRY_AFTER("Retry-After"), SERVER("Server"), VARY("Vary"),
    WWW_AUTHENTICATE("WWW-Authenticate");

    private final String field;

    HttpHeaderField(final String field) {
        this.field = field;
    }

    public static boolean hasField(final String line) {
        return Arrays.stream(values())
                .anyMatch(field -> line.contains(field.getField()));
    }

    public static HttpHeaderField of(final String line) {
        return Arrays.stream(values())
                .filter(field -> line.contains(field.getField()))
                .findAny()
                .orElseThrow(NoMatchHeaderFieldException::new);
    }

    public String getField() {
        return field;
    }
}
