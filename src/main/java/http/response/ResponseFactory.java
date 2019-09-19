package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseFactory {
    private static Map<String, ResponseCreator> responseCreators = new HashMap<>();

    static {
        responseCreators.put("../resources/templates/", new HtmlResponseCreator());
        responseCreators.put("../resources/static/css/", new CssResponseCreator());
        responseCreators.put("../resources/static/js/", new JsResponseCreator());
        responseCreators.put("../resources/static/fonts/", new FontResponseCreator());
        responseCreators.put("redirect://../resources/templates/", new RedirectionResponseCreator());
    }

    public static Response getResponse(String path, String key) {
        ResponseCreator responseCreator = responseCreators.get(key);
        return responseCreator.create(path);
    }
}

class HtmlResponseCreator implements ResponseCreator {
    @Override
    public Response create(String path) {
        return new HtmlResponse(path);
    }
}

class CssResponseCreator implements ResponseCreator {
    @Override
    public Response create(String path) {
        return new CssResponse(path);
    }
}

class JsResponseCreator implements ResponseCreator {
    @Override
    public Response create(String path) {
        return new JsResponse(path);
    }
}

class RedirectionResponseCreator implements ResponseCreator {
    @Override
    public Response create(String path) {
        return new RedirectResponse(path);
    }
}

class FontResponseCreator implements ResponseCreator {
    @Override
    public Response create(String path) {
        return new FontResponse(path);
    }
}