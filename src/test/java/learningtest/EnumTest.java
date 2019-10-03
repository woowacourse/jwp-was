package learningtest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {
    private static final Logger log = LoggerFactory.getLogger(EnumTest.class);

    public enum TestType {
        typeA,
        typeB;

        public static Map<String, TestType> mapInitWithValues = new HashMap<String, TestType>(){{
            for (TestType type : values()) {
                put(type.name(), type);
            }
        }};

        public static Map<String, TestType> mapInitFromClassInfo = new HashMap<String, TestType>(){{
            for (TestType type : TestType.class.getEnumConstants()) {
                put(type.name(), type);
            }
        }};
    }

    @Test
    void static_관련부분에서_enum_값을_어떻게사용할수있는지_확인하기() {
        Map<String, TestType> expectedMap = new HashMap<String, TestType>() {{
            put("typeA", TestType.typeA);
            put("typeB", TestType.typeB);
        }};

        log.debug("mapInitWithValues: {}", TestType.mapInitWithValues);
        log.debug("mapInitFromClassInto: {}", TestType.mapInitFromClassInfo);

        assertThat(TestType.mapInitWithValues.equals(expectedMap)).isFalse();
        assertThat(TestType.mapInitFromClassInfo).isEqualTo(expectedMap);
    }

}
