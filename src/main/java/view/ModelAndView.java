package view;

import java.util.Collections;
import java.util.Map;

public class ModelAndView {

    private Map<String, Object> model;
//    private View view;
    private String view;
    private boolean isRedirect;

    public ModelAndView(String view) {
        this.view = view;
        this.model = Collections.emptyMap();
        this.isRedirect = true;
    }

    public ModelAndView(String view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
        this.isRedirect = false;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    Map<String, Object> getModel() {
        return model;
    }

    public String getView() {
        return view;
    }
}
