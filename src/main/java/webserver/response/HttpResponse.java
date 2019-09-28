package webserver.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpVersion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpVersion httpVersion;
    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(HttpVersion version) {
        this.httpVersion = version;
        this.responseStatus = ResponseStatus.OK;
        this.responseHeaders = new ResponseHeaders();
    }

    private HttpResponse(ResponseStatus responseStatus, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.httpVersion = HttpVersion.HTTP_1_1;
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse sendErrorResponse(ResponseStatus responseStatus) {
        return new HttpResponse(
                responseStatus,
                new ResponseHeaders(),
                new ResponseBody(String.format("error/%d.html", responseStatus.getCode())));
    }

    public void forward(String filePath) {
        responseBody = new ResponseBody(filePath);
        responseHeaders.put("Content-Length", responseBody.getBodyLength());
    }


    public Object getHeader(String key) {
        return responseHeaders.get(key);
    }

    public void addHeader(String key, String value) {
        responseHeaders.put(key, value);
    }

    public void setContentType(String contentType) {
        responseHeaders.put("Content-Type", contentType);
    }

    public void setContentType(MediaType contentType) {
        responseHeaders.put("Content-Type", contentType.getMediaType());
    }

    public void sendRedirect(String uriPath) {
        responseHeaders.put("Location", uriPath);
        setResponseStatus(ResponseStatus.FOUND);
    }

    public List<String> responseBuilder() {
        List<String> responseExport = new ArrayList<>();

        responseExport.add(
                String.format("%s %d %s\r\n", httpVersion.getVersion(), responseStatus.getCode(), responseStatus.name()));
        responseHeaders.keySet().forEach(key ->
                responseExport.add(String.format("%s: %s\r\n", key, responseHeaders.get(key))));
        responseExport.add("\r\n");

        if (responseBody != null) {
            responseExport.add(new String(responseBody.getBody()));
        }
        return responseExport;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getViewPath() {
        return responseBody.getPath();
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void templateForward(String filePath, Map<String, Object> model) {
        String appliedTemplate = null;
        try {
            Template template = getHandlebars().compile(filePath);
            appliedTemplate = template.apply(model);
        } catch (IOException e) {
            logger.error("filePath : {}, model: {}", filePath, model);
        }
        responseBody = new ResponseBody(appliedTemplate.getBytes());
        responseHeaders.put("Content-Length", responseBody.getBodyLength());
    }

    private Handlebars getHandlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("plusOne", (context, options) -> (Integer) context + 1);
        return handlebars;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpVersion=" + httpVersion +
                ", responseStatus=" + responseStatus +
                ", responseHeaders=" + responseHeaders +
                ", responseBody=" + responseBody +
                '}';
    }
}
