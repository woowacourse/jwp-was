package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestParser;
import webserver.http.HttpSessionManager;
import webserver.http.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public abstract class BaseControllerTest {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    public HttpRequest getDefaultHttpRequest(String fileLocation) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileLocation);
            InputStreamReader inputStream = new InputStreamReader(fileInputStream);
            return HttpRequestParser.parseRequest(inputStream, new HttpSessionManager());
        } catch (IOException e) {
            log.debug(e.getMessage(), e.getCause());
        }
        return null;
    }

    public HttpRequest getDefaultHttpRequest(byte[] httpRequestByte) {
        try {
            InputStreamReader inputStream = new InputStreamReader(new ByteArrayInputStream(httpRequestByte));
            return HttpRequestParser.parseRequest(inputStream, new HttpSessionManager());
        } catch (IOException e) {
            log.debug(e.getMessage(), e.getCause());
        }
        return null;
    }

    public ModelAndView mapAndHandle(HttpRequest request, HttpResponse response) {
        Method method = ControllerMapper.mappingMethod(request, response);
        return ControllerAdapter.executeMethod(request, response, method);
    }
}
