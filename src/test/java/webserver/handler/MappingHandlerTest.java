package webserver.handler;

import org.junit.jupiter.api.Test;
import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MappingHandlerTest {
    @Test
    void getServlets_request_true() {
        HttpServlet homeServlet = MappingHandler.getServlets("/");
        HttpServlet userCreateServlet = MappingHandler.getServlets("/user/create");

        assertThat(homeServlet).isInstanceOf(HomeServlet.class);
        assertThat(userCreateServlet).isInstanceOf(UserCreateServlet.class);
    }

    @Test
    void getServlets_staticFile_true() {
        HttpServlet fileServlet = MappingHandler.getServlets("/not_exists_uri");
        assertThat(fileServlet).isInstanceOf(FileServlet.class);
    }
}