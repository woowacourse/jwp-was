package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParameterParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;

public class HttpBody {
    private static final Logger log = LoggerFactory.getLogger(HttpBody.class);
    public static final HttpBody EMPTY_BODY = HttpBody.of("");

    private final String body;

    private HttpBody(String body) {
        this.body = body;
    }

    public static HttpBody of(String body) {
        try {
            return new HttpBody(URLDecoder.decode(body, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("디코딩에 실패했습니다.");
            throw new ServerErrorException("디코딩에 실패했습니다.");
        }
    }

    Map<String, String> convertBodyToMap() {
        return ParameterParser.parse(body);
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpBody httpBody = (HttpBody) o;
        return Objects.equals(body, httpBody.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public String toString() {
        return body;
    }
}
