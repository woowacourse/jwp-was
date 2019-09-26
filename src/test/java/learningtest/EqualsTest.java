package learningtest;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsTest {


    @Test
    void mapWithProperEquals() {
        Map<String, String> m = new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }};

        Map<String, String> mOfSameKeyValues = new HashMap<String, String>() {{
            put("key2", "value2");
            put("key1", "value1");
        }};

        assertThat(m.equals(mOfSameKeyValues)).isTrue();
        assertThat(Objects.deepEquals(m, mOfSameKeyValues)).isTrue();
    }

    @Test
    void mapWithNotProperEquals() {
        Map<String, String[]> m = new HashMap<String, String[]>() {{
            put("key1", new String[]{"value1"});
            put("key2", new String[]{"value2"});
        }};

        Map<String, String[]> mOfSameKeyValues = new HashMap<String, String[]>() {{
            put("key2", new String[]{"value2"});
            put("key1", new String[]{"value1"});
        }};

        assertThat(m.equals(mOfSameKeyValues)).isFalse();
        assertThat(Objects.deepEquals(new String[]{"value1"}, new String[]{"value1"})).isTrue();

        // 내가 생각하는 deepEquals 이 아니다.. ㅠㅠ
        // 살펴보면 HashMap 의 equals 구현이 value 를 equals 로 비교하기 때문.
        assertThat(Objects.deepEquals(m, mOfSameKeyValues)).isFalse();
    }
}
