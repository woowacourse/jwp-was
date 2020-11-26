package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.MissingRequestParameterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RequestParametersTest {

    @DisplayName("RequestParameters 생성자 성공")
    @ParameterizedTest
    @ValueSource(strings = {"userId=javajigi&password=password&name=JaeSung"})
    void constructor(String input) {
        assertThat(new RequestParameters(input)).isNotNull();
    }

    @DisplayName("param value 가져오기 - 성공, 정상")
    @ParameterizedTest
    @ValueSource(strings = {"userId=javajigi&password=password&name=JaeSung"})
    void getValue_Normal(String input) {
        RequestParameters requestParameters = new RequestParameters(input);
        assertThat(requestParameters.getValue("userId")).isEqualTo("javajigi");
        assertThat(requestParameters.getValue("password")).isEqualTo("password");
        assertThat(requestParameters.getValue("name")).isEqualTo("JaeSung");
    }

    @DisplayName("param value 가져오기 - 성공, value가 없을 경우")
    @ParameterizedTest
    @ValueSource(strings = {"userId=javajigi&password=&name="})
    void getValue_EmptyValue(String input) {
        RequestParameters requestParameters = new RequestParameters(input);
        assertThat(requestParameters.getValue("userId")).isEqualTo("javajigi");
        assertThat(requestParameters.getValue("password")).isEqualTo("");
        assertThat(requestParameters.getValue("name")).isEqualTo("");
    }

    @DisplayName("param value 가져오기 - 성공, 중복된 key가 있을 경우")
    @ParameterizedTest
    @ValueSource(strings = {"userId=javajigi&userId=bingbong&password="})
    void getValue_DuplicateKey(String input) {
        RequestParameters requestParameters = new RequestParameters(input);
        assertThat(requestParameters.getValue("userId")).isEqualTo("javajigi,bingbong");
        assertThat(requestParameters.getValue("password")).isEqualTo("");
    }

    @DisplayName("param value 가져오기 - 성공, '&' (ParamDelimiter) 가 없을 경우")
    @ParameterizedTest
    @ValueSource(strings = {"userId=bingbongpassword=hello"})
    void getValue_NotExistParamDelimiter(String input) {
        RequestParameters requestParameters = new RequestParameters(input);
        assertThat(requestParameters.getValue("userId")).isEqualTo("bingbongpassword=hello");
    }

    @DisplayName("param value 가져오기 - 성공, '=' (ParamKeyValueDelimiter) 가 없을 경우")
    @ParameterizedTest
    @ValueSource(strings = {"userId=bingbong&password"})
    void getValue_NotExistParamKeyValueDelimiter(String input) {
        RequestParameters requestParameters = new RequestParameters(input);
        assertThat(requestParameters.getValue("userId")).isEqualTo("bingbong");
        assertThat(requestParameters.getValue("password")).isEqualTo("");
    }

    @DisplayName("param value 가져오기 - 예외, key가 없을 경우")
    @ParameterizedTest
    @ValueSource(strings = {"userId=javajigi"})
    void getValue_NotExistKey_ThrownException(String input) {
        RequestParameters requestParameters = new RequestParameters(input);
        assertThatThrownBy(() -> requestParameters.getValue("hello"))
            .isInstanceOf(MissingRequestParameterException.class);
    }
}
