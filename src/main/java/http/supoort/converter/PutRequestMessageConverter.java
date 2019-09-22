package http.supoort.converter;

import http.model.HttpMethod;
import http.model.HttpRequest;

import java.io.BufferedReader;

public class PutRequestMessageConverter extends AbstractRequestMessageConverter {
    @Override
    public HttpRequest convert(String uri, String protocol, BufferedReader bufferedReader) {
        return convertWithBody(HttpMethod.PUT, uri, protocol, bufferedReader);
    }
}
