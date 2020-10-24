package webserver.http.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import webserver.http.request.HttpBody;
import webserver.http.request.HttpHeader;

public abstract class AbstractHttpService {
    protected final HttpHeader header;
    protected final HttpBody body;

    protected AbstractHttpService(HttpHeader header, HttpBody body) {
        this.header = header;
        this.body = body;
    }

    public abstract void doService(OutputStream out) throws URISyntaxException, IOException;
}
