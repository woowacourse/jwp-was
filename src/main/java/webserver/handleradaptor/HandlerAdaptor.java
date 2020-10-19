package webserver.handleradaptor;

import java.lang.reflect.Method;

import webserver.messageconverter.HttpMessageConverter;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;

public interface HandlerAdaptor {
    ModelAndView invoke(Method handler, ServletRequest servletRequest, HttpMessageConverter converter);
}
