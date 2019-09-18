package http.common;

import http.utils.HttpUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

public class HttpBody {
    private Map<String, String> entity;

    private HttpBody(Map<String, String> entity) {
        this.entity = entity;
    }

    public static HttpBody of(BufferedReader br, HttpHeader httpHeader) throws IOException {
        String contentLength = httpHeader.getHeader("Content-Length");
        if (contentLength == null) {
            return null;
        }
        String httpBody = URLDecoder.decode(IOUtils.readData(br, Integer.parseInt(contentLength)), "UTF-8");
        return new HttpBody(HttpUtils.parseQuery(httpBody));
    }

    public String getEntityValue(String key) {
        return entity.get(key);
    }
}
