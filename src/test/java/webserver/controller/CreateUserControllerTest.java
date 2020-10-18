package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.TestIOUtils.convertText;
import static utils.TestIOUtils.createOutputStream;
import static utils.TestIOUtils.createRequestBufferedReader;
import static utils.TestIOUtils.createResponseBufferedReader;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class CreateUserControllerTest {

    @DisplayName("CreateUserController doPost")
    @Test
    void doPost() throws IOException {
        HttpRequest request = new HttpRequest(createRequestBufferedReader("Http_Request_POST_Create_User.txt"));

        String fileName = "Http_Response_Redirect.txt";
        HttpResponse response = new HttpResponse(createOutputStream(fileName));

        Controller controller = new CreateUserController();
        controller.service(request, response);
        String responseText = convertText(createResponseBufferedReader(fileName));

        assertThat(responseText).contains("HTTP/1.1 302 Found");
        assertThat(responseText).contains("Location: /index.html");
        assertThat(responseText).contains("</body></html>");
    }
}
