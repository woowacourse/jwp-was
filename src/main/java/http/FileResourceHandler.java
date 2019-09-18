package http;

public class FileResourceHandler implements HttpRequestHandler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        return new ModelAndView(getViewByUri(httpRequest.getUri()));
    }

    private View getViewByUri(HttpUri uri) {
        return new View(uri);
    }
}
