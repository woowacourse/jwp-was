package kr.wootecat.dongle.utils;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {

    @Override
    public String create() {
        return UUID.randomUUID().toString();
    }
}
