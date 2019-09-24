package was.http;

import domain.servlet.LoginServlet;
import was.http.servlet.Servlet;
import domain.servlet.IndexServlet;
import domain.servlet.SignupServlet;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private static final Map<String, Servlet> MAP = new HashMap<>();

    private Router() { }

    static {
        // TODO: 설정 파일로부터 읽어오거나 Component Scan 방식으로 자동 입력하도록
        MAP.put("/", new IndexServlet());
        MAP.put("/signup", new SignupServlet());
        MAP.put("/login", new LoginServlet());
    }

    public Servlet getServlet(String path) {
        return MAP.get(path);
    }

    public static Router getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final Router INSTANCE = new Router();
    }
}
