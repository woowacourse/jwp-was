package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.HttpRequest;
import web.HttpResponse;
import web.filter.FilterChain;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(dos);

            FilterChain filterChain = new FilterChain();
            filterChain.doFilter(httpRequest, httpResponse);

//            byte[] body;
//            if (ResourceFilter.isResource(requestPath) && HttpMethod.GET == httpRequest.getMethod()) {
//                ResourceFilter resourceFilter = ResourceFilter.findResourceFilter(requestPath);
//                body = resourceFilter.getBody(requestPath);
//                HttpResponse httpResponse = new HttpResponse(dos);
//                httpResponse.response200Header(body.length, resourceFilter.getContentType());
//                httpResponse.responseBody(body);
//            } else if (requestPath.equals("/user/create") && HttpMethod.POST == httpRequest.getMethod()) {
//                RequestBody requestBody = httpRequest.getRequestBody();
//                Map<String, String> parsedBody = requestBody.parse();
//                User user = new User(parsedBody.get("userId"),
//                        parsedBody.get("password"),
//                        parsedBody.get("name"),
//                        parsedBody.get("email"));
//                DataBase.addUser(user);
//                body = user.toString().getBytes();
//
//                HttpResponse httpResponse = new HttpResponse(dos);
//                httpResponse.response302Header("/index.html");
//                httpResponse.responseBody(body);
//            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
