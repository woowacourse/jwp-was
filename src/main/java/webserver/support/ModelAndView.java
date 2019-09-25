package webserver.support;

import com.google.common.collect.Maps;

import java.util.Map;

public class ModelAndView {
    private final String view;
    private Map<String, Object> models = Maps.newHashMap();

    public ModelAndView(String view) {
        this.view = view;
    }

    public void setModels(String key, Object value) {
        this.models.put(key, value);
    }

    public Map<String, Object> getModels() {
        return this.models;
    }

    public String getView() {
        return this.view;
    }
}
