package webserver;

import java.lang.reflect.Method;

public interface HandlerAdaptor {
    ModelAndView invoke(Method handler, ServletRequest servletRequest, HttpMessageConverter converter);
}
