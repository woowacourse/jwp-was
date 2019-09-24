package http.response;

import http.request.HttpRequest;
import http.request.core.RequestPath;
import http.response.core.Response;
import http.response.core.ResponseBody;
import http.response.core.ResponseCreator;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseFactory {
    private static final Map<String, ResponseCreator> responseCreators = new HashMap<>();

    static {
        responseCreators.put("static", new StaticResponseCreator());
        responseCreators.put("templates", new DynamicResponseCreator());
    }

    public static void sendResponse(HttpRequest httpRequest, DataOutputStream dos) throws IOException, URISyntaxException {
        RequestPath requestPath = httpRequest.getRequestPath();
        Response response = requestPath.getFullPath().contains("/static") ?
                responseCreators.get("static").create(httpRequest) : responseCreators.get("templates").create(httpRequest);
        doResponse(dos, response.doResponse());
    }

    private static void doResponse(DataOutputStream dos, ResponseBody body) throws IOException, URISyntaxException {
        List<Object> bodyInfo = body.getBody();
        dos.writeBytes(String.valueOf(bodyInfo.get(0)));
        if (bodyInfo.size() == 2) {
            byte[] byteBody = (byte[]) bodyInfo.get(1);
            dos.write(byteBody, 0, byteBody.length);
        }
        dos.flush();
    }

}