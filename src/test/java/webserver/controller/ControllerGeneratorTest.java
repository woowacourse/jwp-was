package webserver.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerGeneratorTest {
	@DisplayName("리소스 파일 컨트롤러 생성")
	@Test
	void generateControllerWithResource() {
		String path = "/index.html";
		Controller controller = ControllerGenerator.generateController(path);
		assertThat(controller).isInstanceOf(ResourceController.class);
	}

	@DisplayName("회원가입 시 CreatUserController 생성")
	@Test
	void generateControllerWhenCreateUser() {
		String path = "/user/create";
		Controller controller = ControllerGenerator.generateController(path);
		assertThat(controller).isInstanceOf(CreateUserController.class);
	}

	@DisplayName("로그인 시 UserLoginController 생성")
	@Test
	void generateControllerWhenUserLogin() {
		String path = "/user/login";
		Controller controller = ControllerGenerator.generateController(path);
		assertThat(controller).isInstanceOf(UserLoginController.class);
	}

	@DisplayName("회원 조회 시 UserListController 생성")
	@Test
	public void generateUserListController() {
		String path = "/user/list";
		Controller controller = ControllerGenerator.generateController(path);
		assertThat(controller).isInstanceOf(UserListController.class);
	}
}