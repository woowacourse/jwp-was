package web;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {

    @DisplayName("RequestHeader 생성")
    @Test
    public void create() throws IOException {

        String input = "Host: localhost:8080" + System.lineSeparator()
            + "Connection: keep-alive" + System.lineSeparator()
            + "Accept: */*";

        BufferedReader br = new BufferedReader(new StringReader(input));
        RequestHeader header = RequestHeader.from(br);

        assertThat(header.getParams()).hasSize(3);
        assertThat(header.getValue("Host")).isEqualTo("localhost:8080");
    }

}