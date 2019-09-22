package support;

import was.webserver.WebServer;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTestClient {
    private final BufferedReader in;
    private final PrintWriter out;
    private final Socket socket;

    public HttpTestClient(String host, int port) throws IOException {
        WebServer webServer = new WebServer();
        Thread thread = new Thread(webServer);
        thread.start();

        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
    }

    public String send(String request) {
        // TODO: 문자열로 request 를 받고 문자열로 response 를 반환하는 형태에서 HttpRequest, HttpResponse 객체를 활용하도록 리팩토링
        out.print(request);
        out.flush();

        List<String> lines = in.lines().collect(Collectors.toList());
        return String.join("\n", lines);
    }

    public void close() throws IOException {
        socket.close();
    }
}
