package webserver.response;

import java.util.Map;

public class ModelAndView {
    private String filePath;
    private Map<String, Object> model;

    public ModelAndView(String filePath, Map<String, Object> model) {
        this.filePath = filePath;
        this.model = model;
    }

    public String getFilePath() {
        return filePath;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
