package kr.wootecat.dongle.application;

public class IdGeneratorFactory {

    private IdGeneratorFactory() {
    }

    public static IdGenerator create() {
        return new RandomIdGenerator();
    }
}
