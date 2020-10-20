package webserver.http.request;

import static java.util.Collections.*;
import static java.util.stream.Collectors.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class HttpParams {
    private static final String PARAM_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> params;

    private HttpParams(Map<String, String> params) {
        this.params = params;
    }

    public static HttpParams of(String queryParams) {
        if (queryParams.isEmpty()) {
            return new HttpParams(emptyMap());
        }

        Map<String, String> params = Arrays.stream(queryParams.split(PARAM_DELIMITER))
                .collect(toMap(
                        queryParam -> queryParam.split(VALUE_DELIMITER)[KEY],
                        queryParam -> queryParam.split(VALUE_DELIMITER)[VALUE]
                ));

        return new HttpParams(params);
    }

    public <T> T toModel(Class<T> clazz) {
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
        }
        throw new AssertionError("인자를 통해 인스턴스를 생성하지 못했습니다.");
    }
}
