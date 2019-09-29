package webserver;

import webserver.http.HttpResponse;

import java.io.DataOutputStream;

public interface ViewProcessor {
    boolean isSupported(View view);

    void process(DataOutputStream dos, View view, HttpResponse httpResponse);
}
