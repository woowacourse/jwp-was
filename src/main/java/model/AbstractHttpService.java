package model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public abstract class AbstractHttpService {
    protected final HttpHeader header;
    protected final HttpBody body;

    protected AbstractHttpService(HttpHeader header, HttpBody body) {
        this.header = header;
        this.body = body;
    }

    public abstract void doService(OutputStream out) throws URISyntaxException, IOException;
}
