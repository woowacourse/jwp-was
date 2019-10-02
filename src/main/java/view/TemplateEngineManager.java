package view;

import java.util.Map;

public interface TemplateEngineManager {
    byte[] applyCompile(String viewName, Map<String, Object> model);
}
