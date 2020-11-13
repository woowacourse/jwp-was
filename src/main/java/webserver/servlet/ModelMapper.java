package webserver.servlet;

import static java.util.stream.Collectors.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import webserver.http.request.HttpParams;

public class ModelMapper {
    public static <T> T toModel(Class<T> clazz, HttpParams params) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            Class<?>[] classes = Arrays.stream(fields)
                    .map(Field::getType)
                    .collect(toList())
                    .toArray(new Class<?>[fields.length]);

            Constructor<T> constructor = clazz.getDeclaredConstructor(classes);

            Object[] values = Arrays.stream(fields)
                    .map(Field::getName)
                    .map(params::get)
                    .toArray();

            return constructor.newInstance(values);
        } catch (InstantiationException |
                InvocationTargetException |
                NoSuchMethodException |
                IllegalAccessException e) {
            e.printStackTrace();
            throw new AssertionError("인자를 통해 인스턴스를 생성하지 못했습니다.");
        }
    }
}
