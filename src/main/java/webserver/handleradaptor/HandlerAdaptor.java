package webserver.handleradaptor;

import java.lang.reflect.Method;

import webserver.messageconverter.HttpMessageConverter;
import webserver.response.ModelAndView;
import webserver.request.ServletRequest;

public interface HandlerAdaptor {
    ModelAndView invoke(Method handler, ServletRequest servletRequest, HttpMessageConverter converter);
}
