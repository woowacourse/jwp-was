package http.response;

import exception.PathNotFoundException;
import http.request.Request;

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

    public static Response getResponse(Request request) {
        String responseCreatorKey = responseCreators.keySet().stream()
                .filter(key -> (request.getRequestPath().getPath()).contains(key))
                .findAny()
                .orElseThrow(PathNotFoundException::new);

        ResponseCreator responseCreator = responseCreators.get(responseCreatorKey);
        return responseCreator.create(request);
    }

    static class HtmlResponseCreator implements ResponseCreator {
        @Override
        public Response create(Request request) {
            return new HtmlResponse(request);
        }
    }

    static class CssResponseCreator implements ResponseCreator {
        @Override
        public Response create(Request request) {
            return new CssResponse(request);
        }
    }

    static class JsResponseCreator implements ResponseCreator {
        @Override
        public Response create(Request request) {
            return new JsResponse(request);
        }
    }

    static class RedirectionResponseCreator implements ResponseCreator {
        @Override
        public Response create(Request request) {
            return new RedirectResponse();
        }
    }

    static class FontResponseCreator implements ResponseCreator {
        @Override
        public Response create(Request request) {
            return new FontResponse(request);
        }
    }
}

