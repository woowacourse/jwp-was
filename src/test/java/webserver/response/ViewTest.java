package webserver.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.ServletFixture;

class ViewTest {

    public static final String INDEX_VIEWNAME = "index";
    public static final String REDIRECT_INDEX_VIEWNAME = "redirect:/index";

    @DisplayName("Default View를 생성한다.")
    @Test
    void createDefaultView() {
        View view = View.of(INDEX_VIEWNAME);

        assertThat(view).isInstanceOf(DefaultView.class);
        assertThat(view).extracting(View::getView).isEqualTo(ServletFixture.INDEX_VIEW.getBytes());
        assertThat(view).extracting(View::getViewName).isEqualTo(INDEX_VIEWNAME);
        assertThat(view).extracting(View::isRedirect).isEqualTo(false);
    }

    @DisplayName("Redirect View를 생성한다.")
    @Test
    void createRedirectView() {
        View view = View.of(REDIRECT_INDEX_VIEWNAME);

        assertThat(view).isInstanceOf(RedirectView.class);
        assertThat(view).extracting(View::getView).isEqualTo(ServletFixture.INDEX_VIEW.getBytes());
        assertThat(view).extracting(View::getViewName).isEqualTo(REDIRECT_INDEX_VIEWNAME);
        assertThat(view).extracting(View::isRedirect).isEqualTo(true);
    }
}
