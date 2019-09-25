package domain.servlet;

import domain.service.LoginService;
import was.http.context.BasicSessionHandler;
import was.http.context.SessionHandler;
import was.http.request.HttpRequest;
import was.http.response.HttpResponse;
import was.http.servlet.AbstractServlet;

import java.util.UUID;

public class LoginServlet extends AbstractServlet {
    @Override
    protected HttpResponse doGet(HttpRequest request) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.forward("./templates/user/login.html");
        return httpResponse;
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        SessionHandler sessionHandler = BasicSessionHandler.getInstance();
        UUID sessionId = UUID.randomUUID();
        sessionHandler.addSession(sessionId);
        HttpResponse httpResponse = new HttpResponse();
        LoginService loginService = LoginService.getInstance();
        if (loginService.validate(request.getBody("userId"), request.getBody("password"))) {
            httpResponse.setCookie("SESSIONID", sessionId.toString());
            httpResponse.sendRedirect("/");
            return httpResponse;
        }
        // TODO: 로그인 실패! 500 에러 리턴
        return httpResponse;
    }
}
