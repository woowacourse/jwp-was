package web;

import static utils.HttpRequestParser.*;
import static web.RequestHeader.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class RequestBody {
    Map<String, String> requestBody;

    public RequestBody(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (line == null || line.isEmpty() || NEW_LINE.equals(line)) {
            line = br.readLine();
        }
        this.requestBody = parsingData(line);
    }

    public Map<String, String> getFormData() {
        return requestBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RequestBody that = (RequestBody)o;
        return Objects.equals(requestBody, that.requestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestBody);
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "requestBody=" + requestBody +
                '}';
    }
}
