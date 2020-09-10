package webserver.servlet;

public interface Servlet {
    void service();
    String getResourcePathToRedirect();
}
