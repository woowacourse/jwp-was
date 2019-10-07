package webserver;

import webserver.engine.HandleBar;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    public static final String NON_STATIC_FILE_PATH = "/templates";
    private Map<String, Object> models;
    private byte[] view;

    public void applyTemplateEngine(String viewName) throws IOException {
        HandleBar handleBar = new HandleBar();

        handleBar.setPrefix(NON_STATIC_FILE_PATH);
        String[] splitedViewName = viewName.split("\\.");
        handleBar.setSuffix("." + splitedViewName[1]);
        handleBar.compile(splitedViewName[0]);
        view = handleBar.apply(this.models);
    }

    public ModelAndView() {
        this.models = new HashMap<>();
    }

    public void addModel(String key, Object model) {
        models.put(key, model);
    }

    public byte[] getView() {
        return view;
    }

    public Map<String, Object> getModels() {
        return Collections.unmodifiableMap(models);
    }
}
