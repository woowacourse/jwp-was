package webserver.servlet;

import java.util.Map;

public interface Servlet {
    void service(Map<String, String> parameters);
    String getResourcePathToRedirect();
}
