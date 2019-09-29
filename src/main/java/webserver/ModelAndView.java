package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.engine.TemplateEngine;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModelAndView {
    private static final Logger logger = LoggerFactory.getLogger(ModelAndView.class);
    public static final String NON_STATIC_FILE_PATH = "/templates";
    private Map<String, Object> models;
    private byte[] view;

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

    public void applyTemplateEngine(String viewName) throws IOException {
        TemplateEngine templateEngine = new TemplateEngine();

        templateEngine.setPrefix(NON_STATIC_FILE_PATH);
        String[] splitedViewName = viewName.split("\\.");
        templateEngine.setSuffix("." + splitedViewName[1]);
        templateEngine.compile(splitedViewName[0]);
        view = templateEngine.apply(this.models);
    }
}
