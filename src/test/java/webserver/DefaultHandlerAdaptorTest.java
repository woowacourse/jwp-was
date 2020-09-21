package webserver;

import static org.assertj.core.api.Assertions.*;
import static webserver.ServletFixture.*;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;
import model.User;
import webserver.controller.IndexController;
import webserver.controller.UserController;
import webserver.handleradaptor.DefaultHandlerAdaptor;
import webserver.messageconverter.DefaultHttpMessageConverter;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;
import webserver.response.StatusCode;

class DefaultHandlerAdaptorTest {

    @DisplayName("INDEX 메소드를 정상 실행하고, 결과를 반환한다.")
    @Test
    void invokeIndex() throws NoSuchMethodException {
        DefaultHandlerAdaptor adaptor = new DefaultHandlerAdaptor();
        Method method = IndexController.class.getMethod("index");
        ServletRequest request = new ServletRequest(REQUEST_START_LINE_WITH_HTML, REQUEST_HEADER_WITH_HTML,
            REQUEST_BODY);
        DefaultHttpMessageConverter converter = new DefaultHttpMessageConverter();

        ModelAndView expected = ModelAndView.of(StatusCode.OK, Maps.newHashMap(), Maps.newHashMap(), "index");
        ModelAndView actual = adaptor.invoke(method, request, converter);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("USER CREATE 메소드를 정상 실행하고, 결과를 반환한다.")
    @Test
    void invokeUserCreate() throws NoSuchMethodException {
        DefaultHandlerAdaptor adaptor = new DefaultHandlerAdaptor();
        Method method = UserController.class.getMethod("create", User.class);
        ServletRequest request = new ServletRequest(REQUEST_START_LINE, REQUEST_HEADER, REQUEST_BODY);
        DefaultHttpMessageConverter converter = new DefaultHttpMessageConverter();

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Location", "http://localhost:8080/index.html");
        ModelAndView expected = ModelAndView.of(StatusCode.FOUND, headers, Maps.newHashMap(), "index");
        ModelAndView actual = adaptor.invoke(method, request, converter);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
