package web;

import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMethod;
import webserver.http.request.RequestParams;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.response.ResponseHeaders;
import webserver.http.response.ResponseStatusLine;

public class UserController extends Controller {
    public HttpResponse createUser(HttpRequest httpRequest) {
        if (httpRequest.getHttpStartLine().getHttpMethod() != RequestMethod.POST) {
            return notAllowed(httpRequest);
        }

        RequestParams requestParams = RequestParams.from(httpRequest.getHttpBody().getBody());

        try {
            DataBase.addUser(
                new User(requestParams.getOneParameterValue("userId"), requestParams.getOneParameterValue("password"),
                    requestParams.getOneParameterValue("name"), requestParams.getOneParameterValue("email")));
        } catch (Exception e) {
            return badRequest(httpRequest);
        }

        ResponseStatusLine responseStatusLine = new ResponseStatusLine(httpRequest.getHttpStartLine().getHttpVersion(),
            HttpStatus.FOUND);
        Map<String, String> headersInfo = new HashMap<>();
        headersInfo.put("Location", "/index.html");
        return new HttpResponse(responseStatusLine, new ResponseHeaders(headersInfo));
    }
}
