package webserver.requestmapping;

import java.util.Arrays;
import java.util.List;

import http.request.HttpMethod;
import http.request.RequestEntity;
import webserver.requestmapping.behavior.FileMappingBehavior;
import webserver.requestmapping.behavior.UserCreateBehavior;

public class RequestMappingStorage {

    public static final String FILE_PATH = "/static/files";

    private static List<RequestMapping> REQUEST_MAPPINGS;

    static {
        REQUEST_MAPPINGS = Arrays.asList(
            new RequestMapping(HttpMethod.GET, FILE_PATH, new FileMappingBehavior()),
            new RequestMapping(HttpMethod.POST, "/user/create", new UserCreateBehavior())
        );
    }

    public static RequestMapping findMatchingMapping(RequestEntity requestEntity) {
        return REQUEST_MAPPINGS.stream()
            .filter(requestMapping ->
                requestMapping.isMapping(requestEntity.getHttpMethod(), requestEntity.getHttpUrl().getPath()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("해당 mapping이 존재하지 않습니다."));
    }
}
