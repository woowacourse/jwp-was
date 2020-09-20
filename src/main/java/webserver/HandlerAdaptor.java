package webserver;

import java.lang.reflect.Method;

public interface HandlerAdaptor {
    ServletResponse invoke(Method handler, ServletRequest servletRequest, HttpMessageConverter converter);
}
