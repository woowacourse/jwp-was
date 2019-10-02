package view;

import java.util.Map;

public interface TemplateManager {
    byte[] applyCompile(String viewName, Map<String, Object> modelMap);
}
