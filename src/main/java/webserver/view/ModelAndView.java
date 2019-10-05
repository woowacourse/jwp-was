package webserver.view;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModelAndView {
    private final String viewName;
    private Map<String, Object> model;

    public ModelAndView(final String viewName, Map<String, Object> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public ModelAndView(String viewName) {
        this(viewName, new LinkedHashMap<>());
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public boolean isSameViewExtension(String fileExtension) {
        return viewName.contains(fileExtension);
    }
}
