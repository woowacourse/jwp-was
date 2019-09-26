package http.parameter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParametersTest {
    @Test
    @DisplayName("파라미터가 null 인 경우")
    void queryStringIsNull() {
        // Arrange & Act
        Parameters emptyParameters = Parameters.fromQueryString(null);

        // Assert
        assertThat(emptyParameters.isEmpty()).isTrue();
        assertThat(emptyParameters.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("파라미터 없는 경우")
    void queryStringHasNoParameter() {
        // Arrange
        String emptyQueryString = "";

        // Act
        Parameters emptyParameters = Parameters.fromQueryString(emptyQueryString);

        // Assert
        assertThat(emptyParameters.isEmpty()).isTrue();
        assertThat(emptyParameters.size()).isEqualTo(0);
    }

    @Test
    @DisplayName(" = 로 분리되지 않는 파라미터 입력")
    void queryStringHasWrongParameter() {
        // Arrange
        String queryStringWithoutValue = "hasNotValue";

        // Act
        Parameters emptyParameters = Parameters.fromQueryString(queryStringWithoutValue);

        // Assert
        assertThat(emptyParameters.isEmpty()).isTrue();
        assertThat(emptyParameters.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("파라미터 한개 있는 경우")
    void queryStringHasOneParameter() {
        // Arrange
        String queryStringOfOneParameter = "name=Mr. ko";

        // Act
        Parameters emptyParameters = Parameters.fromQueryString(queryStringOfOneParameter);

        // Assert
        assertThat(emptyParameters.isEmpty()).isFalse();
        assertThat(emptyParameters.size()).isEqualTo(1);
        assertThat(emptyParameters.getParameter("name")).isEqualTo("Mr. ko");
    }

    @Test
    @DisplayName("파라미터 여러개 있는 경우")
    void queryStringHasSeveralParameters() {
        // Arrange
        String emptyQueryString = "name=Mr. ko&age=28";

        // Act
        Parameters emptyParameters = Parameters.fromQueryString(emptyQueryString);

        // Assert
        assertThat(emptyParameters.isEmpty()).isFalse();
        assertThat(emptyParameters.size()).isEqualTo(2);
        assertThat(emptyParameters.getParameter("name")).isEqualTo("Mr. ko");
        assertThat(emptyParameters.getParameter("age")).isEqualTo("28");
    }

    @Test
    @DisplayName("인코딩된 파라미터")
    void queryStringHasEncodedParameter() {
        // Arrange
        String queryStringOfEncodedParameter = "name=%eb%af%b8%ec%8a%a4%ed%84%b0%ec%bd%94";
        String decodedValue = "미스터코";

        // Act
        Parameters emptyParameters = Parameters.fromQueryString(queryStringOfEncodedParameter);

        // Assert
        assertThat(emptyParameters.isEmpty()).isFalse();
        assertThat(emptyParameters.size()).isEqualTo(1);
        assertThat(emptyParameters.getParameter("name")).isEqualTo("미스터코");
    }

    @Test
    @DisplayName("getParameter, 존재하지 않는 파라미터 요청")
    void getParameter_notExistingParameter() {
        // Arrange
        String queryString = "hello=world";
        Parameters parameters = Parameters.fromQueryString(queryString);

        // Act & Assert
        assertThrows(ParameterNotFoundException.class, () -> parameters.getParameter("not existing key"));
    }

    @Test
    @DisplayName("keySet, 존재하지 않는 파라미터 요청")
    void keySet_includeAllKeys() {
        // Arrange
        String queryString = "hello=hhh&world=hhh";
        Parameters parameters = Parameters.fromQueryString(queryString);

        // Act & Assert
        Set<String> keySet = parameters.keySet();
        assertThat(keySet.size()).isEqualTo(2);
        assertThat(keySet.contains("hello")).isTrue();
        assertThat(keySet.contains("world")).isTrue();
    }

    @Test
    void plus_parametersWithoutDuplicatedKey() {
        // Arrange
        Parameters parameters1 = Parameters.fromQueryString("param1=hello");
        Parameters parameters2 = Parameters.fromQueryString("param2=world");

        // Act
        Parameters mergedParameters = parameters1.plus(parameters2);

        // Assert
        assertThat(mergedParameters != parameters1).isTrue();
        assertThat(mergedParameters.size()).isEqualTo(2);
        assertThat(mergedParameters.getParameter("param1")).isEqualTo("hello");
        assertThat(mergedParameters.getParameter("param2")).isEqualTo("world");
    }

    @Test
    void plus_parametersWithDuplicatedKey_OverwritePreviousValue() {
        // Arrange
        Parameters parameters1 = Parameters.fromQueryString("param1=hello");
        Parameters parameters2 = Parameters.fromQueryString("param1=world");

        // Act
        Parameters mergedParameters = parameters1.plus(parameters2);

        // Assert
        assertThat(mergedParameters != parameters1).isTrue();
        assertThat(mergedParameters.size()).isEqualTo(1);
        assertThat(mergedParameters.getParameter("param1")).isEqualTo("world");
    }
}