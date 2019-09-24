package http.supoort.converter.request;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;

import java.io.BufferedReader;

public class DeleteHttpRequestMethodConverter extends AbstractHttpRequestMessageconverter {
    @Override
    public ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader) {
        return convertWithoutBody(HttpMethod.DELETE, uri, protocol, bufferedReader);
    }
}
