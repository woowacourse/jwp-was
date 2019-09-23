package http.supoort.converter;

import http.model.HttpMethod;
import http.model.ServletRequest;

import java.io.BufferedReader;

public class DeleteRequestMethodConverter extends AbstractRequestMessageConverter {
    @Override
    public ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader) {
        return convertWithoutBody(HttpMethod.DELETE, uri, protocol, bufferedReader);
    }
}
