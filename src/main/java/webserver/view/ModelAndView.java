package webserver.view;

import java.util.Map;
import java.util.Objects;

public class ModelAndView {
    View view;
    Map<String, Object> modelMap;

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(View view, Map<String, Object> modelMap) {
        this.view = view;
        this.modelMap = modelMap;
    }

    public View getView() {
        return view;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelAndView that = (ModelAndView) o;
        return Objects.equals(view, that.view) &&
                Objects.equals(modelMap, that.modelMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(view, modelMap);
    }
}
