package web.servlet;

import db.DataBase;
import model.User;
import web.HttpRequest;
import web.HttpResponse;
import web.RequestBody;

import java.util.Map;

public class DispatcherServlet {

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        //servlet 찾아서 태우기
        String requestPath = httpRequest.getRequestPath();
        if (requestPath.equals("/user/create") && httpRequest.getMethod().isPost()) {
            RequestBody requestBody = httpRequest.getRequestBody();
            Map<String, String> parsedBody = requestBody.parse();
            User user = new User(parsedBody.get("userId"),
                    parsedBody.get("password"),
                    parsedBody.get("name"),
                    parsedBody.get("email"));
            DataBase.addUser(user);
            byte[] body = user.toString().getBytes();

            httpResponse.response302Header("/index.html");
            httpResponse.responseBody(body);
        }
    }
}
