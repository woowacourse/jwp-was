package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import utils.IOUtils;

@Getter
public class RequestHeader {

    private final RequestMethod requestMethod;
    private final Map<String, String> queryParam;
    private final String path;
    private final int contentLength;

    public RequestHeader(String header) throws UnsupportedEncodingException {
        String[] lines = header.split("\n");
        String firstLineOfHeader = lines[0];
        this.requestMethod = RequestMethod.valueOf(firstLineOfHeader.split(" ")[0]);
        this.queryParam = new HashMap<>();

        String url = firstLineOfHeader.split(" ")[1];
        int delimiterIndex = url.indexOf("?");

        if (delimiterIndex != -1) {
            this.path = url.substring(0, delimiterIndex);
            String query = url.substring(delimiterIndex + 1);
            IOUtils.addParameters(query, this.queryParam);
        } else {
            this.path = url;
        }

        Map<String, String> headerParams = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(": ");
            headerParams.put(keyValue[0], keyValue[1]);
        }

        this.contentLength = Integer.parseInt(headerParams.getOrDefault("Content-Length", "0"));
    }

    public boolean hasQueryParam() {
        return !queryParam.isEmpty();
    }

    public boolean isTemplate() {
        return path.contains(".html");
    }

    public boolean equalPath(String target) {
        return this.path.equals(target);
    }
}
