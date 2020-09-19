package web;

import exception.InvalidRequestParamsException;
import exception.RequestParameterNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.ParameterMapper;

public class ParameterMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(ParameterMapperTest.class);
    class ParamsMapper extends ParameterMapper {
        public void setParameters(String value) {
            mappingParameters(value);
        }
    }
    
    @Test
    @DisplayName("ParameterMapper를 상속한 클래스에 생성 요청 시 올바르게 생성된다.")
    void createParameterMapperTest() {
        try {
            ParamsMapper paramsMapper = new ParamsMapper();
            Assertions.assertThat(paramsMapper).isNotNull();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @ParameterizedTest
    @CsvSource(value={"poo, bar", "test, success", "cookie, monster"})
    @DisplayName("ParameterMapper의 mappingParameters에 올바른 값을 넣으면 정상적으로 수행된다.")
    void mappingParametersTest(String key, String value) {
        ParamsMapper paramsMapper = new ParamsMapper();
        String params = key+"="+value;

        paramsMapper.setParameters(params);

        Assertions.assertThat(paramsMapper.getParameterByKey(key)).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings={"poobar","=poo","=", "hey="})
    @DisplayName("예외 테스트: ParameterMapper의 mappingParameters에 잘못된 값을 넣으면 예외가 발생한다.")
    void mappingParametersInvalidExceptionTest(String value) {
        ParamsMapper paramsMapper = new ParamsMapper();

        Assertions.assertThatThrownBy(() -> paramsMapper.setParameters(value))
                .isInstanceOf(InvalidRequestParamsException.class)
                .hasMessage("Request에 포함된 인자의 값이 올바르지 않습니다");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("예외 테스트: ParameterMapper의 mappingParameters에 비어있는 값을 넣으면 예외가 발생한다.")
    void mappingParametersNullAndEmptyExceptionTest(String value) {
        ParamsMapper paramsMapper = new ParamsMapper();

        Assertions.assertThatThrownBy(() -> paramsMapper.setParameters(value))
                .isInstanceOf(InvalidRequestParamsException.class)
                .hasMessage("Request에 포함된 인자의 값이 올바르지 않습니다");
    }

    @Test
    @DisplayName("getParameterByKey를 호출 시, 올바른 값을 반환한다.")
    void getParametersByKeyTest() {
        ParamsMapper paramsMapper = new ParamsMapper();
        String params = "poo=bar&key=value&TWICE=good";

        paramsMapper.setParameters(params);
        Assertions.assertThat(paramsMapper.getParameterByKey("poo")).isEqualTo("bar");
        Assertions.assertThat(paramsMapper.getParameterByKey("key")).isEqualTo("value");
        Assertions.assertThat(paramsMapper.getParameterByKey("TWICE")).isEqualTo("good");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("예외 테스트 : getParameterByKey를 호출 시, 빈 값을 전달하면 예외가 발생한다.")
    void getParametersByKeyNullAndEmptyExceptionTest(String value) {
        ParamsMapper paramsMapper = new ParamsMapper();
        String params = "poo=bar&key=value&TWICE=good";

        paramsMapper.setParameters(params);

        Assertions.assertThatThrownBy(() -> paramsMapper.getParameterByKey(value))
                .isInstanceOf(RequestParameterNotFoundException.class)
                .hasMessage("해당 값을 인자 목록에서 찾을 수 없습니다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"bar", "123", "OHMYGIRL"})
    @DisplayName("예외 테스트 : getParameterByKey를 호출 시, 빈 값을 전달하면 예외가 발생한다.")
    void getParametersByKeyInvalidExceptionTest(String value) {
        ParamsMapper paramsMapper = new ParamsMapper();
        String params = "poo=bar&key=value&TWICE=good";

        paramsMapper.setParameters(params);

        Assertions.assertThatThrownBy(() -> paramsMapper.getParameterByKey(value))
                .isInstanceOf(RequestParameterNotFoundException.class)
                .hasMessage(value + " : 해당 값을 인자 목록에서 찾을 수 없습니다");
    }
}
