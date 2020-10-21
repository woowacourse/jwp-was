package webserver;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.controller.UserController;
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

	@DisplayName("MappedRequest에 따라 Method를 반환한다.")
	@Test
	void get() {
		assertThat(RequestMapper.get(mappedRequest)).isEqualTo(userCreateMethod);
	}
}