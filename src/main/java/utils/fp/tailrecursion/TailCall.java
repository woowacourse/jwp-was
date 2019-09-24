package utils.fp.tailrecursion;

@FunctionalInterface
public interface TailCall<T> extends TailRecursion<T> {
    @Override
    default T done() {
        return null;
    }
}