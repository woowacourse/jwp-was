package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Headers {

    private static final String EMPTY_LINE = "";
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<Header, String> headers;

    private Headers(Map<Header, String> headers) {
        this.headers = headers;
    }

    public static Headers of(BufferedReader bufferedReader) throws IOException {
        Map<Header, String> headers = new HashMap<>();

        String line = bufferedReader.readLine();
        while (Objects.nonNull(line) && !line.equals(EMPTY_LINE)) {
            Header key = Header.of(line.split(HEADER_KEY_VALUE_SEPARATOR)[KEY_INDEX]);
            String value = line.split(HEADER_KEY_VALUE_SEPARATOR)[VALUE_INDEX];
            headers.put(key, value);
            line = bufferedReader.readLine();
        }
        return new Headers(Collections.unmodifiableMap(headers));
    }

    public boolean hasContent() {
        return headers.containsKey(Header.CONTENT_LENGTH);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Headers headers1 = (Headers) o;
        return Objects.equals(headers, headers1.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}


