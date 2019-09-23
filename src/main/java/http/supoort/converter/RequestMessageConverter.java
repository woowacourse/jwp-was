package http.supoort.converter;

import http.model.ServletRequest;

import java.io.BufferedReader;

public interface RequestMessageConverter {
    ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader);
}
