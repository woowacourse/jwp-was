package http.supoort.converter;

import http.model.HttpRequest;

import java.io.BufferedReader;

public interface RequestMessageConverter {
    HttpRequest convert(String uri, String protocol, BufferedReader bufferedReader);
}
