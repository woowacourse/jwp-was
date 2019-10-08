package utils.fp.tuple;

public interface Tuple<A, B> {
    default int size() {
        return 2;
    };

    A fst();

    B snd();
}