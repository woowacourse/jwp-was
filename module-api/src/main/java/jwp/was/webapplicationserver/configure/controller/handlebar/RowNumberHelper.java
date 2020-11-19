package jwp.was.webapplicationserver.configure.controller.handlebar;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import java.util.Objects;

public class RowNumberHelper implements Helper<Integer> {

    @Override
    public Object apply(Integer context, Options options) {
        if (Objects.isNull(context)) {
            return null;
        }
        return context + 1;
    }
}
