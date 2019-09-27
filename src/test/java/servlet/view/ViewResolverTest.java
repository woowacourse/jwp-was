package servlet.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.response.HttpServletResponse;

import java.io.IOException;

public class ViewResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolverTest.class);

    @Test
    @DisplayName("ViewResolver 테스트")
    public void test() throws IOException {
        View view = resolve();
        logger.info(view.getPage());
    }

    public static View resolve() throws IOException {
        HttpServletResponse httpServletResponse = new HttpServletResponse();
        httpServletResponse.addParameter("name", "javajigi");
        httpServletResponse.addParameter("email", "javajigi@email.com");
        httpServletResponse.forward("/user/profile.html");

        return ViewResolver.resolve(httpServletResponse);
    }
}