package http;

public abstract class AbstractServlet implements Servlet {
    public void service(HttpRequest request, HttpResponse response) {
        String method = request.getMethod();
        if (method.equals("GET")) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) {

    }

    protected void doGet(final HttpRequest request, final HttpResponse response) {

    }
}
