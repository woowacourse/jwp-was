package application.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.model.UserDto;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class ViewResolver {
	private static final Logger log = LoggerFactory.getLogger(ViewResolver.class);

	public static String resolve(String url, List<UserDto> users) throws IOException {
		log.debug("url : {}, users : {}", url, users);

		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		Handlebars handlebars = new Handlebars(loader);
		handlebars.registerHelper("indexModifier", (Helper<Integer>)(index, options) -> index + 1);

		Template template = handlebars.compile(url);

		Map<String, Object> model = new HashMap<>();
		model.put("users", users);
		return template.apply(model);
	}
}
