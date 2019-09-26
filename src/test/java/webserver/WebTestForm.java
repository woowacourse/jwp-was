package webserver;

public class WebTestForm {
    public String getStartLine(String path) {
        return "GET " + path + "HTTP1.1";
    }

    public String postStartLine(String path) {
        return "POST" + path + "HTTP1.1";
    }
}
