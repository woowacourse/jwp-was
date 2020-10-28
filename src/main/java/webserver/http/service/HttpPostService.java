package webserver.http.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.http.request.HttpBody;
import webserver.http.request.HttpHeader;
import webserver.http.request.HttpParams;
import webserver.http.request.HttpVersion;

public class HttpPostService extends AbstractHttpService {
    private static final Logger logger = LoggerFactory.getLogger(HttpPostService.class);

    public HttpPostService(HttpHeader header, HttpBody body) {
        super(header, body);
    }

    @Override
    public void doService(OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        HttpParams bodyParams = HttpParams.of(body.getBody());
        DataBase.addUser(bodyParams.toModel(User.class));
        response302Header(dos);
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes(HttpVersion.HTTP_1_1.getVersion() + " 302 Found \r\n");
            dos.writeBytes(
                    "Location: http://localhost:8080/index.html \r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
