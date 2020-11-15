package kr.wootecat.dongle.utils;

public class IdGeneratorFactory {

    private IdGeneratorFactory() {
    }

    public static IdGenerator create() {
        return new RandomIdGenerator();
    }
}
