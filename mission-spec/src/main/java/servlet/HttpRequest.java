package servlet;

public interface HttpRequest {

    RequestMethod getMethod();

    String getHeader(String headerName);

    String getParameter(String key);

    boolean hasPathOfStaticFile();

    RequestMethod getRequestMethod();

    String getPath();

    StaticFileType findExtension();

    int findContentLength();

    HttpSession getSession();
}
