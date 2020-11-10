package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.net.HttpHeaders;
import webserver.exception.BadRequestException;
import webserver.exception.HandlerNotFoundException;
import webserver.exception.MethodNotAllowedException;
import webserver.exception.ResourceNotFoundException;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.request.MimeType;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connectionSocket;

    public RequestHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connectionSocket.getInetAddress(),
                connectionSocket.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                connectionSocket.getInputStream(), Charsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream())) {
            final HttpRequest request = HttpRequestParser.parse(br);
            final HttpResponse response = new HttpResponse();
            try {
                final HandlerMappings handlerMappings = new HandlerMappings(
                        Arrays.asList(new StaticResourceHandlerMapping(),
                                new ServletHandlerMapping(ServletMapper.getInstance())));
                final HandlerMapping handler = handlerMappings.findHandler(request);
                handler.handle(request, response);
            } catch (BadRequestException e) {
                final byte[] body = e.getMessage().getBytes(Charsets.UTF_8);
                response.changeHttpStatus(HttpStatus.NOT_FOUND);
                response.addHeader(HttpHeaders.CONTENT_TYPE, MimeType.HTML_UTF_8.getMimeType());
                response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
                response.addBody(body);
            } catch (ResourceNotFoundException | HandlerNotFoundException e) {
                final byte[] body = "404 NOT FOUND".getBytes(Charsets.UTF_8);
                response.changeHttpStatus(HttpStatus.NOT_FOUND);
                response.addHeader(HttpHeaders.CONTENT_TYPE, MimeType.HTML_UTF_8.getMimeType());
                response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
                response.addBody(body);
            } catch (MethodNotAllowedException e) {
                final byte[] body = "405 METHOD NOT ALLOWED".getBytes(Charsets.UTF_8);
                response.changeHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
                response.addHeader(HttpHeaders.CONTENT_TYPE, MimeType.HTML_UTF_8.getMimeType());
                response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
                response.addBody(body);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                final byte[] body = "500 INTERNAL SERVER ERROR".getBytes(Charsets.UTF_8);
                response.changeHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.addHeader(HttpHeaders.CONTENT_TYPE, MimeType.HTML_UTF_8.getMimeType());
                response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
                response.addBody(body);
            }
            ResponseSender.send(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName());
        }
    }
}
