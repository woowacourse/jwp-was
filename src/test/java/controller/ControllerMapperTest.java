package controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(ControllerMapperTest.class);

    @DisplayName("ControllerMapper에 /user/create로 요청 시, CreateUserController로 연결한다.")
    @Test
    void mappingCreateUserControllerTest() {
        String path = "/user/create";
        Controller controller = ControllerMapper.mappingControllerByPath(path);

        Assertions.assertThat(controller).isInstanceOf(CreateUserController.class);
    }

    @DisplayName("ControllerMapper에 /로 요청 시, IndexController로 연결한다.")
    @Test
    void mappingIndexControllerTest() {
        String path = "/";
        Controller controller = ControllerMapper.mappingControllerByPath(path);

        Assertions.assertThat(controller).isInstanceOf(IndexController.class);
    }

    @DisplayName("ControllerMapper에 정해지지 않은 경로로 요청 시, ResourceController로 연결한다.")
    @Test
    void mappingResourceControllerTest() {
        String path = "/kafka/is/great";
        Controller controller = ControllerMapper.mappingControllerByPath(path);

        Assertions.assertThat(controller).isInstanceOf(ResourceController.class);
    }
}
