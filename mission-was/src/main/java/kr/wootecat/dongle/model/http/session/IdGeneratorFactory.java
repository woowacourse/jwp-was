package kr.wootecat.dongle.model.http.session;

public class IdGeneratorFactory {

    private IdGeneratorFactory() {
    }

    public static IdGenerator create() {
        return new RandomIdGenerator();
    }
}
