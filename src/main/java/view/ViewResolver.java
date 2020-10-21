package view;

import java.io.DataOutputStream;

import http.response.HttpResponse;

public interface ViewResolver {

    void render(HttpResponse httpResponse, DataOutputStream dataOutputStream);
}
