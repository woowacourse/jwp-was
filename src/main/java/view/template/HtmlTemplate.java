package view.template;

import java.util.Map;

public interface HtmlTemplate {

    byte[] render(String uri, Map<String, Object> objects);
}
