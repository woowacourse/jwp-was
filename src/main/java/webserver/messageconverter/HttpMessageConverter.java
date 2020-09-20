package webserver.messageconverter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.DispatcherServlet;

public interface HttpMessageConverter {
    Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    <T> T convert(Class<T> clazz, Map<String, String> attributes);

    boolean isSupport(Class<?> parameterType, Map<String, String> body);
}
