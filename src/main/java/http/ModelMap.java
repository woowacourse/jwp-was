package http;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap {
    private LinkedHashMap<String, Object> models;

    public ModelMap() {
        this.models = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Object> getModels() {
        return models;
    }

    public ModelMap addAllAttribute(Map<String, Collection> modelMap) {
        if (modelMap != null) {
            this.models.putAll(modelMap);
        }

        return this;
    }
}
