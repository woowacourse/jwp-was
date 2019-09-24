package webserver;

import http.controller.UserCreateController;
import http.controller.ResourcesController;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMapperTest {
    @Test
    void CreateUserController_리턴_테스트() {
        assertThat(RequestMapper.mappingController("/user/create")).isInstanceOf(UserCreateController.class);
    }
    @Test
    void 맵에_존재하지_않는_컨트롤러_요청일_시_정적페이지_컨트롤러를_리턴한다() {
        assertThat(RequestMapper.mappingController("/index.html")).isInstanceOf(ResourcesController.class);
    }
}