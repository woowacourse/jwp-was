package webserver;

import java.util.Map;

public interface HttpMessageConverter {
    <T> T convert(Class<T> clazz, Map<String, String> attributes);
}
