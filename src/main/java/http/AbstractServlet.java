package http;

public abstract class AbstractServlet implements Servlet {
    public HttpResponse service(final HttpRequest request) {
        String method = request.getMethod();
        if (method.equals("GET")) {
            return doGet(request);
        } else {
            return doPost(request);
        }
    }

    protected HttpResponse doPost(final HttpRequest request) {
        // TODO: 기본으로 METHOD_NOT_ALLOWED 에러 리턴하도록
        return null;
    }
    protected HttpResponse doGet(final HttpRequest request) {
        // TODO: 기본으로 METHOD_NOT_ALLOWED 에러 리턴하도록
        return null;
    }
}
