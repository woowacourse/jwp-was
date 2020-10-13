package service;

import db.DataBase;
import java.util.HashMap;
import java.util.Map;
import model.domain.User;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.Request;
import model.response.Response;
import model.response.StatusLine;

public class ApiService {

    private static final String USER_CREATE_REQUEST = "user/create";
    private static final String CREATE_REDIRECT_LOCATION = "/index.html";

    public static Response execute(Request request) {
        Map<Header, String> headers = new HashMap<>();
        Method method = request.getMethod();

        if (request.containsUri(USER_CREATE_REQUEST) && method.equals(Method.POST)) {
            User user = User.of(request.extractParameters());
            DataBase.addUser(user);

            StatusLine statusLine = StatusLine.of(request, Status.FOUND);

            headers.put(Header.LOCATION, CREATE_REDIRECT_LOCATION);

            return Response.of(statusLine, headers, null);
        }
        StatusLine statusLine = StatusLine.of(request, Status.METHOD_NOT_ALLOWED);

        return Response.of(statusLine);
    }
}
