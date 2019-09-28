package webserver.response;

import webserver.Cookie;
import webserver.request.HttpRequest;

public class ResponseMetaDataGenerator {

    private ResponseMetaDataGenerator() {
    }

    public static ResponseMetaData buildDefaultOkMetaData(HttpRequest httpRequest) {
        return ResponseMetaData.Builder
                .builder(httpRequest, HttpStatus.OK)
                .contentType(httpRequest.findContentType())
                .build();
    }

    public static ResponseMetaData buildDefaultFoundMetaData(HttpRequest httpRequest, String location) {
        return ResponseMetaData.Builder
                .builder(httpRequest, HttpStatus.FOUND)
                .contentType(httpRequest.findContentType())
                .location(location)
                .build();
    }

    public static ResponseMetaData buildFailedLoginResponseMetaData(final HttpRequest request, final String location) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.FOUND)
                .setCookie(Cookie.createLoginState(false))
                .location(location)
                .build();
    }

    public static ResponseMetaData buildSuccessfulLoginResponseMetaData(final HttpRequest request, String location, final String sessionId) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.FOUND)
                .setCookie(Cookie.createJSessionIdState(sessionId, "/"))
                .location(location)
                .build();
    }
}
