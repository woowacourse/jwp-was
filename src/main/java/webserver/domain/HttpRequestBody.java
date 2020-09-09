package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import utils.IOUtils;

@Getter
public class HttpRequestBody {

    private Map<String, String> params;

    public HttpRequestBody(String requestBody) throws UnsupportedEncodingException {
        this.params = new HashMap<>();
        if (!requestBody.isEmpty()) {
            IOUtils.addParameters(requestBody, this.params);
        }
    }
}
