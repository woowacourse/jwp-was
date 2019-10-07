package http;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class MapSessionTest {
    @Test
    @DisplayName("존재하지 않을 때, 형변환 적용해도 문제 없는지 확인")
    void _() {
        String notExistName = "notExistName";
        Session session = MapSession.of("id", (id) -> {});

        Optional.ofNullable((User) session.getAttribute(notExistName));
    }
}