package webserver;

import webserver.httpResponse.HttpResponse;

import java.io.DataOutputStream;

public interface HttpResponseProcessor {
    void process(DataOutputStream dos, String resolve, HttpResponse httpResponse);
}
