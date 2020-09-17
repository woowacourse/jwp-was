package web.application.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.server.domain.request.RequestMethod;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class RequestVo {

    private final String path;
    private final RequestMethod requestMethod;

    public static RequestVo of(String path, RequestMethod requestMethod) {
        return new RequestVo(path, requestMethod);
    }
}
