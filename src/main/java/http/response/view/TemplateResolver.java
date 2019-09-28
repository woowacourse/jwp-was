package http.response.view;

import java.io.IOException;
import java.util.Map;

public interface TemplateResolver {
    String getBody(String path, Map<String, Object> model) throws IOException;
}
