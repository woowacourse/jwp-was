package http;

public class Request {

    private String method;
    private String line;

    public Request(String method, String line) {
        this.method = method;
        this.line = line;
    }

    public String getMethod() {
        return method;
    }

    public String getLine() {
        return line;
    }
}
