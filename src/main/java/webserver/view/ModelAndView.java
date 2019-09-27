package webserver.view;

public class ModelAndView {
    String viewName;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public boolean isViewExists() {
        return viewName != null;
    }
}
