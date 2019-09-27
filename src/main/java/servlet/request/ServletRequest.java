package servlet.request;

public interface ServletRequest {
    String getHeader(String key);

    String getCookie(String key);

    String getParameter(String key);
}
