package webserver.httprequesthandler;

import controller.Controller;
import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.ModelAndView;
import view.View;
import view.ViewResolver;

import java.util.Collections;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class HttpServletRequestHandlerTest {
    @Mock
    HttpRequest httpRequest;

    @Mock
    HttpResponse httpResponse;

    @Mock
    Controller controller;

    @Mock
    View view;

    @Mock
    ViewResolver viewResolver = ViewResolver.getInstance();

    @Mock
    Map<String, Controller> handlers;

    @InjectMocks
    HttpServletRequestHandler httpServletRequestHandler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("동적 자원 처리")
    void handleInternal() {
        ModelAndView modelAndView = new ModelAndView("/", Collections.emptyMap());
        given(httpRequest.getPath()).willReturn("/");
        given(handlers.get("/")).willReturn(controller);
        given(httpRequest.getMethod()).willReturn(RequestMethod.GET);
        given(controller.service(httpRequest, httpResponse)).willReturn(modelAndView);
        given(viewResolver.resolve(modelAndView.getViewName())).willReturn(view);

        httpServletRequestHandler.handleInternal(httpRequest, httpResponse);
        verify(httpResponse).setResponseStatus(ResponseStatus.OK);
    }
}