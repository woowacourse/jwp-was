package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class RequestHandlerIntegrationTest {

    private static final String REQUEST =
        "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";

    private static final String RESPONSE_HEADER =
        "HTTP/1.1 200 OK \n"
            + "Content-Type: text/html;charset=utf-8\n"
            + "Content-Length: 6902\n";

    @Test
    void test() {
        // run server side
        Thread thread = new Thread(new Server());
        thread.start();

        // run client side
        try {
            Client client = new Client(makeConnectedSocket());
            String responseHeader = client.sendAndReadResponseHeader(REQUEST);

            // assertThat response is equal to expected
            assertThat(responseHeader).isEqualTo(RESPONSE_HEADER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket makeConnectedSocket() throws IOException {
        while (true) {
            try {
                return new Socket("127.0.0.1", 6666);
            } catch (ConnectException ignored) {
            }
        }
    }

    class Server implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket server = new ServerSocket(6666);
                System.out.println("server Socket started");

                Socket connection = server.accept();
                System.out.println("connection established");

                RequestHandler handler = new RequestHandler(connection);
                handler.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client {
        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;

        public Client(Socket socket) {
            try {
                this.socket = socket;
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        public String sendAndReadResponseHeader(String message) {
            writer.println(message);
            return readResponseHeader();
        }

        private String readResponseHeader() {
            try {
                StringBuilder stringBuilder = new StringBuilder();

                String line = reader.readLine();
                while (line != null && !line.isEmpty()) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                    line = reader.readLine();
                }

                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}