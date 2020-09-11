package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import lombok.Getter;

@Getter
public class HttpRequestBody {

    private RequestParams requestParams;

    public HttpRequestBody(String requestBody) throws UnsupportedEncodingException {
        this.requestParams = new RequestParams(requestBody);
    }

    public Map<String, String> getRequestParams() {
        return requestParams.getParams();
    }
}
