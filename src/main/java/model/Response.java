package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import javax.activation.MimetypesFileTypeMap;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.tika.Tika;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private DataOutputStream dos;
    private String classPath;
    private Map<String, String> header;

    public Response(DataOutputStream dos, String classPath) {
        this.dos = dos;
        this.classPath = classPath;
        header = new LinkedHashMap<>();
    }

    public void response200() {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(classPath).toURI());
            File file = new File(path.toString());

            String mimeType = new Tika().detect(file);

            byte[] body = FileIoUtils.loadFileFromClasspath(classPath);

            header.put("Status", "HTTP/1.1 200 OK \r\n");
            header.put("Content-Type", mimeType + ";charset=utf-8\r\n");
            header.put("Content-Length", body.length + "\r\n");
            System.out.println("길이+"+body.length);
            for (String key : header.keySet()) {
                if(key.equals("Status")) {
                    dos.writeBytes(header.get(key));
                }
                dos.writeBytes(key + ": " + header.get(key));
                System.out.println("키"+header.get(key));
            }
            responseBody(body);

            //forward(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void forward(byte[] body) throws IOException {
        for (String key : header.keySet()) {
            dos.writeBytes(key + ": " + header.get(key));
        }
        responseBody(body);
    }

    public void response300(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
