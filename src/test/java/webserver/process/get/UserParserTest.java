package webserver.process.get;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import webserver.http.request.HttpRequestBody;

class UserParserTest {

	@DisplayName("Http Body에서 User 파싱")
	@Test
	void parseUser() {

		String url = "userId=user&password=1234&name=name&email=email@gmail.com";
		HttpRequestBody httpRequestBody = new HttpRequestBody(url);

		User user = UserParser.parseUser(httpRequestBody);

		assertAll(
			() -> assertEquals(user.getUserId(), "user"),
			() -> assertEquals(user.getPassword(), "1234"),
			() -> assertEquals(user.getName(), "name"),
			() -> assertEquals(user.getEmail(), "email@gmail.com")
		);
	}
}