package view;

import http.response.HttpResponse;
import http.response.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.verify;

class RedirectViewTest {
    @Mock
    HttpResponse httpResponse;

    RedirectView redirectView = new RedirectView("redirect: /");

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void render() {
        redirectView.render(Collections.emptyMap(), httpResponse);

        verify(httpResponse).setResponseStatus(ResponseStatus.FOUND);
        verify(httpResponse).addHeaderAttribute("Location", "/");
    }
}