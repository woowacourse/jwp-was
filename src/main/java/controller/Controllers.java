package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controllers implements Controller {

    private static final String RESOURCE_FILE_PATTERN = "^.+\\.([a-zA-Z]+)$";
    private static final String RESOURCE = "resource";

    private Map<String, Function<HttpRequest, HttpResponse>> finder = new HashMap<>();

    public Controllers() {
        finder.put(HttpMethod.GET + "/index.html", httpRequest -> new IndexController().doGet(httpRequest));
        finder.put(HttpMethod.POST + "/user/create", httpRequest -> new UserCreateController().doPost(httpRequest));
        finder.put(RESOURCE, httpRequest -> new ResourceController().doGet(httpRequest));
    }

    public HttpResponse service(HttpRequest httpRequest) {
        String methodAndPath = httpRequest.getHttpMethod() + httpRequest.getPath();
        if (finder.containsKey(methodAndPath)) {
            return finder.get(methodAndPath).apply(httpRequest);
        }
        if (isResource(methodAndPath)) {
            return finder.get(RESOURCE).apply(httpRequest);
        }
        return null; // 404 error 반환 에정
    }

    private boolean isResource(final String uri) {
        Pattern pattern = Pattern.compile(RESOURCE_FILE_PATTERN);
        Matcher m = pattern.matcher(uri);
        return m.find();
    }
}
