package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHeaderTest {
    private ResponseHeader responseHeader;

    @BeforeEach
    public void setUp() {
        responseHeader = new ResponseHeader();
        responseHeader.setLocation("/index.html");
        responseHeader.setType("text/html");
    }

    @Test
    public void getType() {
        assertThat(responseHeader.getType()).isEqualTo("text/html");
    }

    @Test
    public void getLocation() {
        assertThat(responseHeader.getLocation()).isEqualTo("/index.html");
    }
}