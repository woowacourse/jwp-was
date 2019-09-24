package http.supoort.converter.response;

import http.model.response.ServletResponse;

import java.io.DataOutputStream;

public interface ViewResolver {
    void render(ServletResponse response, DataOutputStream dataOutputStream);
}
