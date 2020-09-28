package jwp.was.webserver.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static util.Constants.EMPTY;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import jwp.was.webserver.exception.NotExistsUrlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ParametersTest {

    private static final String KEY_VALUE_DELIMITER = "=";

    @DisplayName("URL에서 파라미터 생성, 쿼리스트링이 없는 경우 Empty 셋팅")
    @ParameterizedTest
    @ValueSource(strings = {"index.html", "index.html?"})
    void fromUrl_NotExistsQueryString_Empty(String url) {
        Parameters parameters = Parameters.fromUrl(url);

        assertThat(parameters.getParameters()).hasSize(0);
    }

    @DisplayName("URL에서 파라미터 생성, 쿼리스트링이 있는 경우")
    @ParameterizedTest
    @CsvSource(value = {"bodyKey=bodyValue, 1", "bodyKey=bodyValue&bodyKey2=bodyValue2, 2"})
    void fromUrl_ExistsQueryString(String url, int count) {
        url = "index.html?" + url;
        Parameters parameters = Parameters.fromUrl(url);

        assertThat(parameters.getParameters()).hasSize(count);
    }

    @DisplayName("URL에서 파라미터 생성, 예외 발생 - URL PATH가 없는 경우")
    @ParameterizedTest
    @CsvSource(value = {"?bodyKey=bodyValue", "?bodyKey=bodyValue&bodyKey2=bodyValue2"})
    void fromUrl_NotExistsUrlPath_ThrownException(String url) {
        assertThatThrownBy(() -> Parameters.fromUrl(url)).isInstanceOf(NotExistsUrlPath.class);
    }

    @DisplayName("파라미터 생성하기")
    @ParameterizedTest
    @CsvSource(value = {"bodyKey=bodyValue, 1", "bodyKey=bodyValue&bodyKey2=bodyValue2, 2"})
    void fromEncodedParameter(String value, int count) {
        Parameters parameters = Parameters.fromEncodedParameter(value);

        assertThat(parameters.getParameters()).hasSize(count);
    }

    @DisplayName("파라미터 값이 없는 경우 EMPTY 치환")
    @Test
    void fromEncodedParameter_NotExistsValue_TurnEmpty() {
        String key = "bodyKey";
        String parameter = key + "=";
        Parameters parameters = Parameters.fromEncodedParameter(parameter);

        assertThat(parameters.getParameters()).hasSize(1);
        assertThat(parameters.getParameters().get(key)).isEqualTo(EMPTY);
    }

    @DisplayName("파라미터 키/값 구분자가 없는 경우 Drop")
    @ParameterizedTest
    @CsvSource(value = {"bodyKey, 0", "bodyKey=bodyValue&bodyKey2, 1"})
    void fromEncodedParameter_NotExistsKeyValueDelimiter_Drop(String value, int count) {
        Parameters parameters = Parameters.fromEncodedParameter(value);

        assertThat(parameters.getParameters()).hasSize(count);
    }

    @DisplayName("parameter에 중복 Key가 들어온다면, 먼저 들어온 것으로 Value 처리")
    @Test
    void fromEncodedParameter_DuplicateParameterKey_UseFirstValue() {
        String duplicateKey = "bodyKey";
        String firstValue = "bodyValue";
        String parameter = duplicateKey + "=" + firstValue + "&" + duplicateKey + "=bodyValue2";

        Parameters parameters = Parameters.fromEncodedParameter(parameter);

        assertThat(parameters.getParameters()).hasSize(1);
        assertThat(parameters.getParameters().get(duplicateKey)).isEqualTo(firstValue);
    }

    @DisplayName("parameter UTF-8 디코딩")
    @Test
    void fromEncodedParameter_Korean_Utf8Decoding() {
        String encodedKey = "%EA%B0%80%EB%82%98%EB%8B%A4";
        String encodedValue = "%EB%9D%BC%EB%A7%88%EB%B0%94";
        String encodedParameter = encodedKey + "=" + encodedValue;

        String decodedKey = URLDecoder.decode(encodedKey, StandardCharsets.UTF_8);
        String decodedValue = URLDecoder.decode(encodedValue, StandardCharsets.UTF_8);
        assertThat(decodedKey).isEqualTo("가나다");
        assertThat(decodedValue).isEqualTo("라마바");

        Parameters parameters = Parameters.fromEncodedParameter(encodedParameter);

        assertThat(parameters.getParameters()).hasSize(1);
        assertThat(parameters.getParameters().get(decodedKey)).isEqualTo(decodedValue);
    }

    @DisplayName("두 파라미터를 합쳐서 반환")
    @Test
    void combine() {
        String firstKey = "bodyKey1";
        String firstValue = "bodyValue1";
        Parameters firstParameters
            = Parameters.fromEncodedParameter(firstKey + KEY_VALUE_DELIMITER + firstValue);

        String secondKey = "bodyKey2";
        String secondValue = "bodyValue2";
        Parameters secondParameters
            = Parameters.fromEncodedParameter(secondKey + KEY_VALUE_DELIMITER + secondValue);

        Map<String, String> combinedParameters
            = Parameters.combine(firstParameters, secondParameters).getParameters();

        assertThat(combinedParameters).hasSize(
            firstParameters.getParameters().size() + secondParameters.getParameters().size());
        assertThat(combinedParameters.get(firstKey)).isEqualTo(firstValue);
        assertThat(combinedParameters.get(secondKey)).isEqualTo(secondValue);
    }

    // urlPath + body의 조합인 경우, body의 파라미터를 우선
    @DisplayName("파라미터들을 합칠 때, 두 파라미터가 동일한 경우, 뒤의 파라미터로 반영")
    @Test
    void combine_DuplicateParameter_UsedFirstParameters() {
        String duplicateKey = "bodyKey1";
        String firstValue = "bodyValue1";
        Parameters firstParameters
            = Parameters.fromEncodedParameter(duplicateKey + KEY_VALUE_DELIMITER + firstValue);

        String secondValue = "bodyValue2";
        Parameters secondParameters
            = Parameters.fromEncodedParameter(duplicateKey + KEY_VALUE_DELIMITER + secondValue);

        Map<String, String> combinedParameters
            = Parameters.combine(firstParameters, secondParameters).getParameters();

        assertThat(combinedParameters).hasSize(secondParameters.getParameters().size());
        assertThat(combinedParameters.get(duplicateKey)).isEqualTo(secondValue);
    }
}
