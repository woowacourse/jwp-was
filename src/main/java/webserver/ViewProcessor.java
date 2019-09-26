package webserver;

import webserver.http.HttpResponse;

import java.io.DataOutputStream;

public interface ViewProcessor {
    boolean isSupported(String viewName);

    void process(DataOutputStream dos, String viewName, HttpResponse httpResponse);
}
