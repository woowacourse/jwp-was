package model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public interface HttpService {
    void doService(OutputStream out, RequestURI requestURI, HttpBody httpBody) throws IOException, URISyntaxException;
}
