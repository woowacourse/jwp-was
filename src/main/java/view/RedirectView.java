package view;

import http.response.HttpResponse;
import http.response.ResponseStatus;

import java.util.Map;

import static com.google.common.net.HttpHeaders.LOCATION;

public class RedirectView implements View {
    private final String viewName;

    public RedirectView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, Object> model, HttpResponse httpResponse) {
        httpResponse.setResponseStatus(ResponseStatus.FOUND);
        httpResponse.addHeaderAttribute(LOCATION, viewName);
    }
}
