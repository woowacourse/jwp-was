package webserver;

import static webserver.handler.Controller.mapping;
import static webserver.response.Response.emptyResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import webserver.request.Request;
import webserver.request.RequestType;
import webserver.response.Response;

public class WebServerTest {

    private String testDirectory = "./src/test/resources/";

    public Request generateRequest(String request) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + request));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        List<String> lines = new ArrayList<>();

        while (!"".equals(line = br.readLine()) && line != null) {
            lines.add(line);
        }
        return new Request(lines, br);
    }

    public Response generateResponse(Request request) {
        return mapping(RequestType.of(request)).apply(request, emptyResponse());
    }
}
