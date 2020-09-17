package webserver;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.controller.UserController;
import webserver.exception.DuplicatedMappedRequestException;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.MappedRequest;
import webserver.http.response.HttpResponse;

class RequestMapperTest {
	private MappedRequest mappedRequest;
	private Method userCreateMethod;

	@BeforeEach
	void setUp() throws NoSuchMethodException {
		mappedRequest = new MappedRequest(HttpMethod.POST, "/user/create");
		userCreateMethod = UserController.class.getMethod("create", HttpRequest.class, HttpResponse.class);

		RequestMapper.clear();
		RequestMapper.add(mappedRequest, userCreateMethod);
	}

	@DisplayName("중복된 MappedRequest를 추가할 경우 예외처리한다.")
	@Test
	void addThrowExceptionWhenDuplicatedMappedRequest() {

		assertThatThrownBy(() -> RequestMapper.add(mappedRequest, userCreateMethod))
			.isInstanceOf(DuplicatedMappedRequestException.class)
			.hasMessage(
				"중복되는 Mapped Request가 있습니다 : public static java.lang.String application.controller.UserController.create(webserver.http.request.HttpRequest,webserver.http.response.HttpResponse)와 public static java.lang.String application.controller.UserController.create(webserver.http.request.HttpRequest,webserver.http.response.HttpResponse)");
	}

	@DisplayName("MappedRequest에 따라 Method를 반환한다.")
	@Test
	void get() {
		assertThat(RequestMapper.get(mappedRequest)).isEqualTo(userCreateMethod);
	}
}