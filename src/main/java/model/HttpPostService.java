package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;

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
        DataBase.findAll().forEach(user -> logger.debug(user.toString()));
        response302Header(dos);
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + "http://localhost:8080/index.html" + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
