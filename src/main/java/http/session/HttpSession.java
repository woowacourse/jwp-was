package http.session;

public class HttpSession {
    private String id;

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
