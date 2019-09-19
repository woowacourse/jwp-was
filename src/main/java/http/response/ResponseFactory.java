package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseFactory {
    private static Map<String, ResponseCreator> responsCreators = new HashMap<>();

    static {
        responsCreators.put("../resources/templates/", new HtmlResponseCreator());
        responsCreators.put("../resources/static/css/", new CssResponseCreator());
        responsCreators.put("../resources/static/js/", new JsResponseCreator());
        responsCreators.put("../resources/static/fonts/", new FontResponseCreator());
        responsCreators.put("redirect:../resources/templates/", new RedirectionResponseCreator());
    }

    public static Response getResponse(String path) {
        ResponseCreator responseCreator = responsCreators.get(path);
        return responseCreator.create();
    }
}

class HtmlResponseCreator implements ResponseCreator {
    @Override
    public Response create() {
        return new HtmlResponse();
    }
}

class CssResponseCreator implements ResponseCreator {
    @Override
    public Response create() {
        return new CssResponse();
    }
}

class JsResponseCreator implements ResponseCreator {
    @Override
    public Response create() {
        return new JsResponse();
    }
}

class RedirectionResponseCreator implements ResponseCreator {
    @Override
    public Response create() {
        return new RedirectResponse();
    }
}

class FontResponseCreator implements ResponseCreator {
    @Override
    public Response create() {
        return new FontResponse();
    }
}