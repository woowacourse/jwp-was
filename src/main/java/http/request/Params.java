package http.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Params {
    private List<String> params;

    public Params() {
        params = new ArrayList<>();
    }

    public void add(String param) {
        params.add(param);
    }

    public String getValue() {
        return params.isEmpty() ? null : params.get(0);
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(params);
    }
}
