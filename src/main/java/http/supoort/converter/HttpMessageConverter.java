package http.supoort.converter;

import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.supoort.converter.request.RequestMessageConverter;
import http.supoort.converter.response.ResponseMessageConverter;

import java.io.InputStream;

public class HttpMessageConverter {
    private RequestMessageConverter requestConverter;
    private ResponseMessageConverter responseConverter;

    public HttpMessageConverter(RequestMessageConverter requestConverter, ResponseMessageConverter responseConverter) {
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
