package jwp.was.webapplicationserver.configure.controller.handlerbar;

import static org.assertj.core.api.Assertions.assertThat;

import jwp.was.webapplicationserver.configure.controller.handlebar.RowNumberHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RowNumberHelperTest {

    @DisplayName("Apply, +1 반환, 수가 있는 경우")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void apply_ExistsNumber_ReturnPlusOne(Integer value) {
        RowNumberHelper rowNumberHelper = new RowNumberHelper();
        Object result = rowNumberHelper.apply(value, null);

        assertThat(result).isEqualTo(value + 1);
    }

    @DisplayName("Apply, Null 반환, 수가 없는 경우")
    @ParameterizedTest
    @NullSource
    void apply_NotExistsNumber_ReturnNull(Integer value) {
        RowNumberHelper rowNumberHelper = new RowNumberHelper();
        Object result = rowNumberHelper.apply(value, null);

        assertThat(result).isNull();
    }
}
