package view;

import http.response.HttpResponse;

public interface ViewProcessor {
    public byte[] render(ModelAndView modelAndView, HttpResponse response);

    boolean isSupported(String viewName);
}
