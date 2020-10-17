package controller;

import java.util.HashMap;
import java.util.Map;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import service.UserService;

public class UserController extends AbstractController {

    private static final String USER_CREATE_REQUEST = "/user/create";
    private static final String CREATE_REDIRECT_LOCATION = "/index.html";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Method method = httpRequest.getMethod();

        if (method.equals(Method.POST)) {
            return doPost(httpRequest);
        }

        return super.service(httpRequest);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        if (httpRequest.isSameUri(USER_CREATE_REQUEST)) {
            return createUser(httpRequest);
        }

        return HttpResponse.of(Status.NOT_FOUND);
    }

    public static HttpResponse createUser(HttpRequest httpRequest) {
        Map<Header, String> headers = new HashMap<>();
        UserService service = UserService.getInstance();

        service.createUser(httpRequest);

        StatusLine statusLine = StatusLine.of(httpRequest, Status.FOUND);
        headers.put(Header.LOCATION, CREATE_REDIRECT_LOCATION);

        return HttpResponse.of(statusLine, headers, null);
    }
}
