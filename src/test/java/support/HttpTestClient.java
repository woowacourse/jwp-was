package support;

import webserver.WebServer;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTestClient {
    private final BufferedReader in;
    private final PrintWriter out;

    public HttpTestClient(String host, int port) throws IOException {
        WebServer webServer = new WebServer();
        Thread thread = new Thread(webServer);
        thread.start();

        Socket socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
    }

    public String send(String request) {
        out.print(request);
        out.flush();

        List<String> lines = in.lines().collect(Collectors.toList());
        return String.join("\n", lines);
    }
}
