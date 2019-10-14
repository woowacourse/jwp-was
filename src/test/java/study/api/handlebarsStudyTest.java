package study.api;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.api.testmodel.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsStudyTest {
    private static String TEST_DIRECTORY = "/study/api";

    @Test
    @DisplayName("{{this}}를 통해 single String value를 넘겨준다.")
    void thisTest() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hi {{this}}!");

        String templateString = template.apply("Hongsi");

        assertThat(templateString).isEqualTo("Hi Hongsi!");
    }

    @Test
    @DisplayName("{{this}}를 통해 Map을 넘겨준다.")
    void passMapAsContextObject() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hi {{name}}!");
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("name", "Hongsi");

        String templateString = template.apply(parameterMap);

        assertThat(templateString).isEqualTo("Hi Hongsi!");
    }

    @Test
    @DisplayName("Custom하게 만든 Object를 넘겨준다.")
    void passingCustomObject() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hi {{name}}!");
        User user = new User("hongsi", "1234", "Hongsi", "Hongsi@hongsi.com");

        String templateString = template.apply(user);

        assertThat(templateString).isEqualTo("Hi Hongsi!");
    }

    @Test
    @DisplayName("template loader로 html 파일을 읽어와서 Object를 넘겨줄 수 있다.")
    void templateLoader() throws IOException {
        TemplateLoader templateLoader = new ClassPathTemplateLoader(TEST_DIRECTORY, ".html");
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("template-loader-test");
        User user = new User("hongsi", "1234", "Hongsi", "Hongsi@hongsi.com");

        String templateString = template.apply(user);

        assertThat(templateString).isEqualTo("Hi Hongsi!");
    }

    @Test
    @DisplayName("Each helper는 collection을 iterates 하면서 Object를 넘겨준다.")
    void eachHelper() throws IOException {
        TemplateLoader templateLoader = new ClassPathTemplateLoader(TEST_DIRECTORY, ".html");
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("each-helper-test");

        List<User> users = new ArrayList<>();
        Map<String, List<User>> parameterMap = new HashMap<>();

        User user1 = new User("hongsi", "1234", "Hongsi", "Hongsi@hongsi.com");
        User user2 = new User("deock", "1234", "Deock", "deock@deock.com");
        users.add(user1);
        users.add(user2);
        parameterMap.put("users", users);

        String templateString = template.apply(parameterMap);

        assertThat(templateString).isEqualTo("hongsi-1234-Hongsi-Hongsi@hongsi.com"
                + "deock-1234-Deock-deock@deock.com");
    }
}
