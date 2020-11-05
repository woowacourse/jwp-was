package servlet;

public interface HttpResponse {

    void forward(String content);

    void forward(String path, StaticFileType staticFileType);

    void sendRedirect(String path);

    void respondPageNotFound();

    void respondMethodNotAllowed();

    void addCookie(Cookie cookie, String... options);
}
