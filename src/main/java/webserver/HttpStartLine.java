package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpStartLine {
    private final String method;
    private final String target;
    private final String httpVersion;

    public HttpStartLine(String startLine) {
        String[] splitStartLine = startLine.split(" ");
        method = splitStartLine[0];
        target = splitStartLine[1];
        httpVersion = splitStartLine[2];
    }

    public String getMethod() {
        return method;
    }

    public String getTarget() {
        return target;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public boolean isGet() {
        return method.equals("GET");
    }

    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>();
        String[] split = target.split("\\?")[1].split("&");
        for (String pair : split) {
            String[] split1 = pair.split("=");
            String key = split1[0];
            String value = "";
            if (split1.length == 2) {
                value = split1[1];
            }
            parameters.put(key, value);
        }
        return parameters;
    }

    public String getSource() {
        return target.split("\\?")[0];
    }

    public boolean isPost() {
        return method.equals("POST");
    }

    public boolean hasParameters() {
        return target.split("\\?").length == 2;
    }
}
