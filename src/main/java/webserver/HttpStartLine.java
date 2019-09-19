package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpStartLine {
    private final String method;
    private final String target;
    private final String httpVersion;

    private HttpStartLine(String method, String target, String httpVersion) {
        this.method = method;
        this.target = target;
        this.httpVersion = httpVersion;
    }

    public static HttpStartLine of(BufferedReader br) throws IOException {
        String startLine = br.readLine();
        String[] splitStartLine = startLine.split(" ");
        return new HttpStartLine(splitStartLine[0], splitStartLine[1], splitStartLine[2]);
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

    public boolean hasParameters() {
        return target.split("\\?").length == 2;
    }

    public boolean isPost() {
        return method.equals("POST");
    }

    public String getContentType() {
        System.out.println("getSource() = " + getSource());
        String[] split = getSource().split("\\.");
        int length = split.length;
        return split[length - 1];
    }
}
