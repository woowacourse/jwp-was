package http.supoort.converter.request;

import http.model.request.ServletRequest;

import java.io.BufferedReader;

public interface RequestMessageConverter {
    ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader);
}
