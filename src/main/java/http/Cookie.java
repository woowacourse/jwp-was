package http;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Cookie {
    private String name;
    private String value;

    public static List<Cookie> listOf(String cookie) {
        if(Objects.isNull(cookie) || cookie.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.stream(cookie.split("; "))
                .map(value -> new Cookie(value.split("=")[0], value.split("=")[1]))
                .collect(toList());
    }

    public static Cookie of(String name, String value) {
        return new Cookie(name, value);
    }

    private Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
