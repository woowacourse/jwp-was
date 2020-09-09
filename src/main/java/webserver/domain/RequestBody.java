package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import utils.IOUtils;

@Getter
public class RequestBody {

    private Map<String, String> params;

    public RequestBody(String requestBody) throws UnsupportedEncodingException {
        this.params = new HashMap<>();
        if (!requestBody.isEmpty()) {
            IOUtils.addParameters(requestBody, this.params);
        }
    }
}
