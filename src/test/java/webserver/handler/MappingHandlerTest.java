package webserver.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MappingHandlerTest {
    private HttpRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpRequest.class);
    }

    @Test
    void getServlets_request_true() {
        when(mockRequest.getAbsPath()).thenReturn("/");
        when(mockRequest.isFile()).thenReturn(false);
        HttpServlet homeServlet = MappingHandler.getServlets(mockRequest);
        assertThat(homeServlet).isInstanceOf(HomeServlet.class);

        when(mockRequest.getAbsPath()).thenReturn("/user/create");
        HttpServlet userCreateServlet = MappingHandler.getServlets(mockRequest);
        assertThat(userCreateServlet).isInstanceOf(UserCreateServlet.class);
    }

    @Test
    void getServlets_staticFile_true() {
        when(mockRequest.isFile()).thenReturn(true);
        HttpServlet fileServlet = MappingHandler.getServlets(mockRequest);
        assertThat(fileServlet).isInstanceOf(FileServlet.class);
    }
}