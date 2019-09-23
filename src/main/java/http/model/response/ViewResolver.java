package http.model.response;

import java.io.DataOutputStream;

public interface ViewResolver {
    void render(DataOutputStream dataOutputStream);
}
