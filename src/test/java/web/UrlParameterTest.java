package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.request.UrlParameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlParameterTest {

    @DisplayName("=을 기준으로 key, value로 분리한다.")
    @Test
    void parse1() {
        String param = "userId=bebop";
        UrlParameter pair = new UrlParameter(param);

        assertThat(pair.getKey()).isEqualTo("userId");
        assertThat(pair.getValue()).isEqualTo("bebop");
    }

    @DisplayName("전달받은 parameter에 =이 없는 경우")
    @Test
    void parse2() {
        String param = "userId bebop";

        assertThatThrownBy(() -> new UrlParameter(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%s : 올바르지 않은 입력입니다.", param);
    }

    @DisplayName("전달받은 parameter가 비어있는 경우")
    @Test
    void parse3() {
        String param = "";

        assertThatThrownBy(() -> new UrlParameter(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%s : 올바르지 않은 입력입니다.", param);
    }


}
