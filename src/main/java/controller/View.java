package controller;

import http.response.HttpResponse;

import java.util.Map;

public interface View {
    void render(Map<String, Object> model, HttpResponse httpResponse);
}
