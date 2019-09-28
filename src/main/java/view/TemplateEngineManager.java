package view;

import view.exception.TemplateException;

import java.util.Map;

public interface TemplateEngineManager {
    String getCompiledTemplate(String viewName, Map<String, Object> model) throws TemplateException;
}
