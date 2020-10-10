package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import utils.HttpUtils;
import utils.StringUtils;

public class Request {

    private Method method;
    private String location;
    private String parameters;
    private ContentType contentType;

    private Request(String method, String location, String parameters, String contentType) {
        this.method = Method.of(method);
        this.location = location;
        this.parameters = parameters;
        this.contentType = ContentType.of(contentType)
            .orElse(null);
    }

    public static Request of(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();

        String method = StringUtils.extractRequestMethod(line);
        String location = StringUtils.extractRequestLocation(line);
        String parameters = HttpUtils.getParameters(line, method, bufferedReader);
        String contentType = StringUtils.extractExtension(line);

        return new Request(method, location, parameters, contentType);
    }

    public boolean isSameMethod(Method method) {
        return this.method.equals(method);
    }

    public String getLocation() {
        return location;
    }

    public String getParameters() {
        return parameters;
    }

    public ContentType getContentType() {
        return contentType;
    }
}
