package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequests;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class HttpResponse {
    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

    protected HttpRequests httpRequests;

    public void initHttpRequests(HttpRequests httpRequests) {
        this.httpRequests = httpRequests;
    }

    public abstract void handleResponse(DataOutputStream dos) throws IOException, URISyntaxException;
}
