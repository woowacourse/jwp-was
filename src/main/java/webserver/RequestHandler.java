package webserver;

import static http.ResponseHeader.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import db.DataBase;
import exception.IllegalRequestException;
import http.Request;
import http.RequestLine;
import http.RequestMethod;
import mapper.QueryParams;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import utils.IOUtils;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = null;
            Request request = new Request(br);
            RequestLine requestLine = request.getRequestLine();

            if (requestLine.getMethod() == RequestMethod.GET) {
                String requestUrl = requestLine.getUrl();
                if (requestUrl.endsWith(".html")) {
                    body = FileIoUtils.loadFileFromClasspath("./templates" + requestUrl);
                    response200Header(dos, body.length);
                } else {
                    QueryParams queryParams = new QueryParams(requestUrl);
                    Map<String, String> queryParamsMap = queryParams.getQueryParams();
                    User user = new User(queryParamsMap.get("userId"), queryParamsMap.get("password"), queryParamsMap.get("name"), queryParamsMap.get("email"));
                    DataBase.addUser(user);
                }
            } else if (requestLine.getMethod() == RequestMethod.POST) {
                Map<String, String> requestBodies = request.getRequestBody().getRequestBodies();
                User user = new User(requestBodies.get("userId"), requestBodies.get("password"), requestBodies.get("name"), requestBodies.get("email"));
                DataBase.addUser(user);
                response302Header(dos, "/index.html");
            }
            responseBody(dos, body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
