package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class RequestURITypeTest {
    @DisplayName("팩토리 반환")
    @TestFactory
    Stream<DynamicTest> getFactory() {
        return Stream.of(
                dynamicTest("query params가 존재할 때 ParamsFactory에서 생성된 requetURI를 반환",
                        this::existParams),
                dynamicTest("query params가 존재하지 않을 때 NoParamsFactory에서 생성된 requestURI를 반환",
                        this::noParams)
        );
    }

    private void existParams() {
        String uri = "http://localhost:8080/user/create";
        String queryParam = "?name=name";
        RequestURI actual = RequestURIType.of(uri + queryParam)
                .getFactory()
                .create(uri + queryParam);
        assertThat(actual.getUri()).isEqualTo(uri);
    }

    private void noParams() {
        String uri = "http://localhost:8080/user/create";
        RequestURI actual = RequestURIType.of(uri)
                .getFactory()
                .create(uri);
        assertThat(actual.getUri()).isEqualTo(uri);
    }
}
