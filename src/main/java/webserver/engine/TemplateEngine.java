package webserver.engine;

import java.util.Map;

public interface TemplateEngine {
    void compile(String location) throws Exception;

    Object apply(Map<String, Object> model) throws Exception;

    void setPrefix(String prefix);

    void setSuffix(String suffix);
}
