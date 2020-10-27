import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import web.application.controller.Controller;
import web.application.controller.CreateUserController;
import web.application.controller.ListController;
import web.application.controller.RootController;
import web.application.controller.UserLoginController;
import web.application.util.HandlebarsTemplateEngine;
import web.server.dto.UrlMappingCreateDto;

class UrlMapperTest {

    private UrlMapper urlMapper;

    @BeforeEach
    void before() {
        List<UrlMappingCreateDto> urlMappingCreateDtos = Arrays.asList(
            UrlMappingCreateDto.of("/", new RootController()),
            UrlMappingCreateDto.of("/user/create", new CreateUserController()),
            UrlMappingCreateDto.of("/user/login", new UserLoginController()),
            UrlMappingCreateDto.of("/user/list", new ListController(HandlebarsTemplateEngine.getInstance()))
        );
        urlMapper = UrlMapper.from(urlMappingCreateDtos);
    }

    @DisplayName("UrlMapper에서 URL에 따른 컨트롤러를 확인한다.")
    @Test
    void getController() {
        Controller controller = urlMapper.getController("/user/create");

        assertThat(controller).isOfAnyClassIn(CreateUserController.class);
    }

    @DisplayName("UrlMapper에서 Url이 존재하는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"/user/create,true", "/test,false"})
    void contains(String url, boolean expected) {
        boolean actual = urlMapper.contains(url);

        assertThat(actual).isEqualTo(expected);
    }
}