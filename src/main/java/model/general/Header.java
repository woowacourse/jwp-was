package model.general;

import java.util.Arrays;

public enum Header {

    //General Header
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    DATE("Date"),
    PRAGMA("Pragma"),
    TRAILER("Trailer"),
    TRANSFER_ENCO("Transfer-Enco"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    WARNING("Warning"),
    //Request Header
    SEC_CH_UA("sec-ch-ua"),
    SEC_CH_UA_MOBILE("sec-ch-ua-mobile"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    SEC_FETCH_SITE("Sec-Fetch-Site"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"),
    SEC_FETCH_USER("Sec-Fetch-User"),
    SEC_FETCH_DEST("Sec-Fetch-Dest"),
    ORIGIN("Origin"),
    PURPOSE("Purpose"),
    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    AUTHORIZATION("Authorization"),
    EXPECT("Expect"),
    FROM("From"),
    HOST("Host"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    MAX_FORWARDS("Max-Forwards"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    TE("TE"),
    USER_AGENT("User-Agent"),
    //Response Header
    SERVER("Server"),
    ACCEPT_RANGE("Accept-Range"),
    SET_COOKIE("Set-Cookie"),
    ETAG("ETag"),
    PROXY_AUTHENTICATE("Proxy-authenticate"),
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
    LOCATION("Location"),
    CONTENT_DISPOSITION("Content-Disposition"),
    CONTENT_SECURITY_POLICY("Content-Security-Policy"),
    //Entity Header
    ALLOW("Allow"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    EXPIRES("Expires"),
    LAST_MODIFIED("Last-Modified"),
    EXTENSION_HEADER("extension-header");

    String name;

    Header(String name) {
        this.name = name;
    }

    public static Header of(String name) {
        String upperCaseName = name.toUpperCase();
        return Arrays.stream(values())
            .filter(h -> upperCaseName.equals(h.name.toUpperCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown Header"));
    }

    public String getName() {
        return name;
    }
}
