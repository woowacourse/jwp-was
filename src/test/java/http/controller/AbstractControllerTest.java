package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AbstractControllerTest {

    @Test
    void 리퀘스트매핑_중복없음() {
        assertDoesNotThrow(() -> new FileResourceController(RequestMapping.GET("/index.html")));
    }

    @Test
    void 리퀘스트매핑_중복등록() {
        assertThatThrownBy(() -> {
            new FileResourceController(RequestMapping.GET("/index.html"),
                    RequestMapping.GET("/index.html"));
        }).isInstanceOf(IllegalRequestMappingException.class);
    }

    @Test
    void 학습테스트_Set_ContainsAll_대상이_부분집합일때만_True() {
        Set<Integer> integers = new HashSet<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        assertThat(integers.containsAll(Arrays.asList(1, 4, 5))).isFalse();
    }

    @Test
    void 학습테스트_Set_Contains_컬렉션이_하나라도_포함되는지() {
        Set<Integer> integers = new HashSet<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        assertThat(Stream.of(1, 4, 5)
                .anyMatch(integers::contains)).isTrue();

        assertThat(Stream.of(1, 2, 3)
                .anyMatch(integers::contains)).isTrue();
    }
}
