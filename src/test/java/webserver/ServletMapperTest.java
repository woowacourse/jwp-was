package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servlet.DefaultServlet;

import static org.assertj.core.api.Assertions.assertThat;

class ServletMapperTest {
    @Test
    @DisplayName("경로에 맞는 servlet이 없는 경우 DefaultServlet 반환")
    void defaultServlet() {
        assertThat(ServletMapper.getServlet("Wrong path")).isInstanceOf(DefaultServlet.class);
    }
}