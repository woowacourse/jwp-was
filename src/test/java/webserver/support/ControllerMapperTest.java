package webserver.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.controller.CreateUserController;
import webserver.controller.FileController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ControllerMapperTest {
    private ControllerMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ControllerMapper();
    }

    @Test
    public void mapUserCreateController() {
        String url = "/user/create";
        assertThat(mapper.map(url)).isEqualTo(CreateUserController.getInstance());
    }

    @Test
    public void mapFileController() {
        String url = "/";
        assertThat(mapper.map(url)).isEqualTo(FileController.getInstance());
    }
}