package http.supoort.converter.request;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;

import java.io.BufferedReader;

public class DeleteRequestConverter extends AbstractHttpRequestConverter {
    @Override
    public ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader) {
        return convertWithoutBody(HttpMethod.DELETE, uri, protocol, bufferedReader);
    }
}
