package webserver;

import webserver.http.request.HttpRequest;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpRequest httpRequest) {
        System.out.println("회원가입 GET 요청을 처리한다.");
    }

    @Override
    protected void doPost(HttpRequest httpRequest) {
        System.out.println("회원가입 POST 요청을 처리한다.");
    }
}
