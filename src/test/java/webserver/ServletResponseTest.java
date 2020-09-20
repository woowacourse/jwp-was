package webserver;

import static org.assertj.core.api.Assertions.*;
import static webserver.ServletFixture.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.request.ServletRequest;
import webserver.response.ServletResponse;

class ServletResponseTest {

    @DisplayName("정상적으로 Response 생성한다.")
    @Test
    void create() throws IOException {
        ServletRequest servletRequest = new ServletRequest(REQUEST_HEADER, REQUEST_BODY);

        FileOutputStream fos = new FileOutputStream(new File("src/test/resources/response.txt"));
        DataOutputStream dos = new DataOutputStream(fos);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("View", "index");

        ServletResponse response = new ServletResponse(ServletResponse.StatusCode.OK, attributes);
        response.sendResponse(dos, servletRequest);

        dos.close();
        fos.close();
        BufferedReader br2 = getBufferedResponse();

        String line = br2.readLine();
        StringBuilder builder = new StringBuilder();
        builder.append(line);
        while (Objects.nonNull(line)) {
            line = br2.readLine();
            builder.append(line);
        }
        br2.close();

        assertThat(builder.toString()).isEqualTo(ServletFixture.RESPONSE);
    }

    private BufferedReader getBufferedResponse() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/response.txt"));
        InputStreamReader ir = new InputStreamReader(fileInputStream);
        return new BufferedReader(ir);
    }
}
