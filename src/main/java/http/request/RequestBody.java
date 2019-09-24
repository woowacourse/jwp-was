package http.request;

import static http.common.ContentType.FORM_URLENCODED;

public class RequestBody {
    private final String body;
    private final RequestParameter formData;

    public RequestBody(String body, String contentType) {
        this.body = body;
        this.formData = convertFormData(body, contentType);
    }

    private RequestParameter convertFormData(String body, String contentType) {
        if (FORM_URLENCODED.equals(contentType)) {
            return new RequestParameter(body);
        }
        return RequestParameter.EMPTY;
    }

    public String getFormData(String key) {
        return formData.getParameter(key);
    }

    public String getBody() {
        return body;
    }
}
