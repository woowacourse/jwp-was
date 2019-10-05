package webserver.http;

import view.View;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Map<String, Object> model = new HashMap<>();
    private View view;
    private HttpStatus httpStatus;

    public ModelAndView() {
    }

    public ModelAndView(String view) {
        this.view = new View(view);
    }

    public ModelAndView(String view, HttpStatus httpStatus) {
        this.view = new View(view);
        this.httpStatus = httpStatus;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public String getView() {
        if (httpStatus.equals(HttpStatus.REDIRECT)) {
            return "/" + view.getViewPath() + ".html";
        }
        return view.getViewPath();
    }

    public void setView(String view) {
        this.view = new View(view);
    }

    public void setModel(String key, Object value) {
        this.model.put(key, value);
    }

    public boolean isTemplateEngineView() {
        return !this.httpStatus.equals(HttpStatus.REDIRECT)
                && !this.view.getViewPath().contains(".");
    }

    public boolean isRedirectView() {
        return this.httpStatus.equals(HttpStatus.REDIRECT);
    }

    public boolean isResourceFileView() {
        return this.view.getViewPath().contains(".");
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public MediaType getMediaType() {
        if (!view.getViewPath().contains(".")) {
            return MediaType.HTML;
        }
        return MediaType.find(view.getViewPath().substring(view.getViewPath().lastIndexOf(".") + 1));
    }

    public byte[] getByteView() {
        return view.getByteView();
    }

    public void setByteView(byte[] byteView) {
        this.view.setByteView(byteView);
    }
}
