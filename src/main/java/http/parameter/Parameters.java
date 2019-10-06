package http.parameter;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Parameters {
    public static final Parameters EMPTY_PARAMETERS = new Parameters(new HashMap<>());
    private static final Logger log = LoggerFactory.getLogger(Parameters.class);
    private static final String UTF_8 = "utf-8";

    private final Map<String, String> parameters;

    private Parameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static Parameters fromQueryString(String queryString) {
        if (Objects.isNull(queryString)) {
            return EMPTY_PARAMETERS;
        }
        try {
            String decodedQueryString = URLDecoder.decode(queryString, UTF_8);
            return new Parameters(ParameterParser.parse(decodedQueryString));
        } catch (UnsupportedEncodingException e) {
            log.error("error: ", e);
            return EMPTY_PARAMETERS;
        }
    }

    public boolean isEmpty() {
        return 0 == parameters.size();
    }

    public int size() {
        return parameters.size();
    }

    public Set<String> keySet() {
        return parameters.keySet();
    }

    public String getParameter(String key) {
        return Optional.ofNullable(parameters.get(key))
                .orElseThrow(() -> ParameterNotFoundException.ofParameterKey(key));
    }

    public Parameters plus(Parameters parameters2) {
        Map<String, String> newParameters = SerializationUtils.clone(new HashMap<>(this.parameters));

        newParameters.putAll(parameters2.parameters);
        return new Parameters(newParameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
