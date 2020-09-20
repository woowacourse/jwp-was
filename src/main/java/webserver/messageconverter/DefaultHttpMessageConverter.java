package webserver.messageconverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                    logger.info(e.getMessage(), e);
                }
            });
            return instance;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new MethodParameterBindException();
        } catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(clazz);
        }
    }

    @Override
    public boolean isSupport(Class<?> parameterType, Map<String, String> body) {
        List<String> collect = Arrays.stream(parameterType.getDeclaredFields()).map(Field::getName)
            .collect(Collectors.toList());
        return collect.stream()
            .anyMatch(body::containsKey);
    }
}
