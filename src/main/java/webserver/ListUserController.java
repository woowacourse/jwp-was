package webserver;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;

public class ListUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        // TODO: 2020/09/22 추후 Login 구현 과정에서 개발
        if (isLogin("")) {
            System.out.println(DataBase.findAll());
            httpResponse.forward("/user/list.html");
            return;
        }
        httpResponse.sendRedirect("/user/login_failed.html");
    }

    public boolean isLogin(String input) {
        return true;
    }
}
