package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    @Test
    public void setAttributeTest() {
        HttpSession httpSession = new HttpSession("tempId");
        httpSession.setAttribute("logined", "true");

        assertThat(httpSession.getAttribute("logined")).isEqualTo("true");
    }

    @Test
    public void removeAttributeTest() {
        HttpSession httpSession = new HttpSession("tempId");
        httpSession.setAttribute("disliked", "true");
        httpSession.removeAttribute("disliked");

        assertThat(httpSession.getAttribute("disliked")).isEqualTo(null);
    }

    @Test
    public void invalidateTest() {
        HttpSession httpSession = new HttpSession("tempId");
        httpSession.setAttribute("liked", "true");
        httpSession.invalidate();

        assertThat(httpSession.getAttribute("liked")).isEqualTo(null);
    }
}