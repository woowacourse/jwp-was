package jwp.was.webserver.dto;

import static jwp.was.webserver.dto.UrlPath.getUrlPathAndQueryStrings;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Parameters {

    private static final String PARAMETER_VALUE_DELIMITER = "=";
    private static final String PARAMETER_DELIMITER = "&";
    private static final String EMPTY = "";
    private static final int PARAMETER_VALUE_INDEX = 1;
    private static final int PARAMETER_KEY_INDEX = 0;

    private final Map<String, String> parameters;

    public Parameters(Map<String, String> parameters) {
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public static Parameters fromUrl(String url) {
        String[] urlParameters = getUrlPathAndQueryStrings(url);
        if (urlParameters.length == 1) {
            return new Parameters(new HashMap<>());
        }
        String queryString = urlParameters[1];
        return fromEncodedParameter(queryString);
    }

    public static Parameters fromEncodedParameter(String encodedParameter) {
        String decodedParameter = URLDecoder.decode(encodedParameter, StandardCharsets.UTF_8);

        if (Objects.isNull(decodedParameter) || decodedParameter.isEmpty()) {
            return new Parameters(new HashMap<>());
        }
        return new Parameters(makeParameters(decodedParameter));
    }

    private static Map<String, String> makeParameters(String parameters) {
        return Arrays.stream(parameters.split(PARAMETER_DELIMITER))
            .filter(parameter -> parameter.contains(PARAMETER_VALUE_DELIMITER))
            .map(parameter -> parameter.split(PARAMETER_VALUE_DELIMITER))
            .collect(
                Collectors.toMap(
                    parameter -> parameter[PARAMETER_KEY_INDEX],
                    Parameters::makeParameterValue,
                    (p1, p2) -> p1
                )
            );
    }

    private static String makeParameterValue(String[] parameter) {
        if (parameter.length == PARAMETER_VALUE_INDEX) {
            return EMPTY;
        }
        return parameter[PARAMETER_VALUE_INDEX];
    }

    public static Parameters combine(Parameters object1, Parameters object2) {
        Map<String, String> parameters = new HashMap<>();
        parameters.putAll(object1.getParameters());
        parameters.putAll(object2.getParameters());
        return new Parameters(parameters);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String parameter) {
        return parameters.keySet().stream()
            .filter(key -> key.equals(parameter))
            .map(parameters::get)
            .findFirst()
            .orElse(null);
    }
}
