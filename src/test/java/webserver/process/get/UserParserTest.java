package webserver.process.get;

import static org.graalvm.compiler.options.OptionType.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import model.User;

class UserParserTest {

	@Test
	void parseUser() {

		String url = "/user/create?userId=user&password=1234&name=name&email=email@gmail.com";

		User user = UserParser.parseUser(url);

		assertAll(
			() -> assertEquals(user.getUserId(), "user"),
			() -> assertEquals(user.getPassword(), "1234"),
			() -> assertEquals(user.getName(), "name"),
			() -> assertEquals(user.getEmail(), "email@gmail.com")
		);
	}
}