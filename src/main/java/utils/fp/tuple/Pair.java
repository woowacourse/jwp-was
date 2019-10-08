package utils.fp.tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Pair<A, B> implements Tuple<A, B> {
    private final A fst;
    private final B snd;

    public static <A, B> Pair<A, B> of(A fst, B snd) {
        return new Pair<>(fst, snd);
    }

    public static <A, B> Stream<Pair<A, B>> zip(List<A> a, List<B> b) {
        return IntStream.range(0, Math.min(a.size(), b.size()))
                        .mapToObj(i -> new Pair<>(a.get(i), b.get(i)));
    }

    public static <A, B> Stream<Pair<A, B>> zip(List<A> a, Stream<B> b) {
        return zip(a, b.collect(Collectors.toList()));
    }

    public static <A, B> Stream<Pair<A, B>> zip(Stream<A> a, List<B> b) {
        return zip(a.collect(Collectors.toList()), b);
    }

    public static <A, B> Stream<Pair<A, B>> zip(Stream<A> a, Stream<B> b) {
        return zip(a.collect(Collectors.toList()), b.collect(Collectors.toList()));
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(List<A> a, List<B> b) {
        Stream<Pair<A, B>> front = zip(a, b);
        Stream<Pair<A, B>> back = IntStream.range(Math.min(a.size(), b.size()), Math.max(a.size(), b.size()))
                                            .mapToObj(i -> (i < a.size())
                                                    ? new Pair<>(a.get(i), b.get(b.size() - 1))
                                                    : new Pair<>(a.get(a.size() -1), b.get(i))
                );
        return Stream.concat(front, back);
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(List<A> a, Stream<B> b) {
        return zipAndFill(a, b.collect(Collectors.toList()));
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(List<A> a, B b) {
        return zipAndFill(a, Arrays.asList(b));
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(Stream<A> a, List<B> b) {
        return zipAndFill(a.collect(Collectors.toList()), b);
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(Stream<A> a, Stream<B> b) {
        return zipAndFill(a.collect(Collectors.toList()), b.collect(Collectors.toList()));
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(Stream<A> a, B b) {
        return zipAndFill(a.collect(Collectors.toList()), Arrays.asList(b));
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(A a, List<B> b) {
        return zipAndFill(Arrays.asList(a), b);
    }

    public static <A, B> Stream<Pair<A, B>> zipAndFill(A a, Stream<B> b) {
        return zipAndFill(Arrays.asList(a), b.collect(Collectors.toList()));
    }

    public static <T> Stream<Pair<T, Integer>> zipWithIndex(List<T> list) {
        return IntStream.range(0, list.size())
                        .mapToObj(i -> new Pair<>(list.get(i), i));
    }

    public static <T> Stream<Pair<T, Integer>> zipWithIndex(Stream<T> stream) {
        return zipWithIndex(stream.collect(Collectors.toList()));
    }

    public static <T> Stream<T> unzip(Stream<Pair<? extends T, ? extends T>> pairs) {
        return pairs.flatMap(pair -> Stream.of(pair.fst, pair.snd));
    }

    public static <T> Stream<T> unzip(List<Pair<? extends T, ? extends T>> pairs) {
        return unzip(pairs.stream());
    }

    public Pair(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public A fst() {
        return this.fst;
    }

    @Override
    public B snd() {
        return this.snd;
    }

    @Override
    public String toString() {
        return "(" + this.fst.toString() + ", " + this.snd.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> rhs = (Pair<?, ?>) o;
        return Objects.equals(this.fst, rhs.fst) && Objects.equals(this.snd, rhs.snd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fst, this.snd);
    }
}