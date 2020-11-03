package webserver.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.model.UserDto;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class HandlebarsTest {
	private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

	@Test
	void listTemplateTest() throws Exception {
		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		Handlebars handlebars = new Handlebars(loader);
		handlebars.registerHelper("indexModifier", (Helper<Integer>)(index, options) -> index + 1);

		Template template = handlebars.compile("user/list");

		List<UserDto> users = new ArrayList<>();
		users.add(new UserDto("javajigi", "자바지기", "javajigi@gmail.com"));
		users.add(new UserDto("toney", "토니", "toney@gmail.com"));
		Map<String, Object> model = new HashMap<>();
		model.put("users", users);

		String listPage = template.apply(model);
		log.debug("ListPage : {}", listPage);

		assertAll(
			() -> assertThat(listPage).contains("javajigi"),
			() -> assertThat(listPage).contains("자바지기"),
			() -> assertThat(listPage).contains("javajigi@gmail.com"),
			() -> assertThat(listPage).contains("toney"),
			() -> assertThat(listPage).contains("토니"),
			() -> assertThat(listPage).contains("toney@gmail.com")
		);
	}
}
