package http.supoort.converter.request;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;

import java.io.BufferedReader;

public class PutHttpRequestMessageconverter extends AbstractHttpRequestMessageconverter {
    @Override
    public ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader) {
        return convertWithBody(HttpMethod.PUT, uri, protocol, bufferedReader);
    }
}
