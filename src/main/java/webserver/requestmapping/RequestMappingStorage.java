package webserver.requestmapping;

import http.request.HttpMethod;
import http.request.RequestEntity;
import implementedbehavior.UserCreateBehavior;
import implementedbehavior.UserListBehavior;
import implementedbehavior.UserLoginBehavior;

import java.util.Arrays;
import java.util.List;

public class RequestMappingStorage {

    private static List<RequestMapping> REQUEST_MAPPINGS;

    static {
        REQUEST_MAPPINGS = Arrays.asList(
            new RequestMapping(HttpMethod.POST, "/user/create", new UserCreateBehavior()),
            new RequestMapping(HttpMethod.POST, "/user/login", new UserLoginBehavior()),
            new RequestMapping(HttpMethod.GET, "/user/list", new UserListBehavior())
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
