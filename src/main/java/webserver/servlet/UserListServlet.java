package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;

import java.io.IOException;

public class UserListServlet extends  RequestServlet{
    private final String url = "/user/list";
    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return super.doPost(httpRequest, httpResponse);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
