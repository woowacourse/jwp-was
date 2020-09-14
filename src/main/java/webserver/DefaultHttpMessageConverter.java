package webserver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import exception.MethodParameterBindException;
import exception.NoDefaultConstructorException;

public class DefaultHttpMessageConverter implements HttpMessageConverter {
    @Override
    public <T> T convert(Class<T> clazz, Map<String, String> attributes) {
        try {
            T instance = clazz.getConstructor().newInstance();
            attributes.forEach((key, value) -> {
                try {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(instance, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return instance;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new MethodParameterBindException();
        } catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(clazz);
        }
    }
}
