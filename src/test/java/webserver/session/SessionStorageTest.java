package webserver.session;

import helper.StorageHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStorageTest {
    String staticId = "id";
    IdGenerationStrategy generator = StorageHelper.idGenerator(staticId);

    @DisplayName("세션 생성")
    @Test
    void create_staticId_equalId() {
        HttpSession httpSession = SessionStorage.create(generator);
        assertThat(httpSession.getId()).isEqualTo("id");
    }

    @DisplayName("고정 아이디 세션 등록시 존재")
    @Test
    void get_staticId_exists() {
        SessionStorage.create(generator);
        assertThat(SessionStorage.get(staticId)).isNotNull();
    }

    @DisplayName("존재하지 않는 아이디 찾을시 Null")
    @Test
    void get_notExistsId_null() {
        SessionStorage.create(generator);
        assertThat(SessionStorage.get("abc")).isNull();
    }

    @DisplayName("세션 미생성시 Null")
    @Test
    void get_notCreated_null() {
        assertThat(SessionStorage.get(staticId)).isNull();
    }

    @DisplayName("세션 아이디로 존재 여부 확인")
    @Test
    void exists_created_true() {
        SessionStorage.create(generator);
        assertThat(SessionStorage.exists(staticId)).isTrue();
    }

    @DisplayName("UUID로 아이디 생성")
    @Test
    void exists_uuid_exists() {
        HttpSession httpSession = SessionStorage.create(new UUIDGenerationStrategy());
        assertThat(SessionStorage.exists(httpSession.getId())).isTrue();
    }

}