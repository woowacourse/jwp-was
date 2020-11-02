package common;

import http.request.Cookie;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.request.RequestParams;

import java.util.HashMap;
import java.util.Map;

public class TestFixtureFactory {
    public static HttpRequest makeHttpRequestForControllerTest(Map<String, String> params, Map<String, String> cookies) {
        return new HttpRequest(
                new RequestLine(HttpMethod.POST, ""),
                new RequestHeader(new HashMap<>()),
                new RequestParams(params),
                new Cookie(cookies)
        );
    }
}
