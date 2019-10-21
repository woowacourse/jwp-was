package http.session;

public class TestIdGenerator implements IdGenerator {
    public static final String GENERATED_TEST_ID = "testId";

    @Override
    public String generateId() {
        return GENERATED_TEST_ID;
    }
}
