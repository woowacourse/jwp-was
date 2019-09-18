package utils;

public class Procedure {
    private static Procedure instance = new Procedure();

    public static Procedure instance() {
        return instance;
    }

    private Procedure() {}
}