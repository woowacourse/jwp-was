package http.supoort.converter;

import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.supoort.converter.request.RequestConverter;
import http.supoort.converter.response.ResponseConverter;

import java.io.InputStream;

public class HttpMessageConverter {
    private RequestConverter requestConverter;
    private ResponseConverter responseConverter;

    public HttpMessageConverter(RequestConverter requestConverter, ResponseConverter responseConverter) {
        this.requestConverter = requestConverter;
        this.responseConverter = responseConverter;
    }

    public ServletRequest parse(InputStream inputStream) {
        return requestConverter.parse(inputStream);
    }

    public void response(ServletResponse response) {
        responseConverter.convert(response);
    }
}
