package utils.recursion;

public interface TailRecursion<T> {
    default T get() {
        TailRecursion<T> trampoline = this;
        while (trampoline.done() == null) {
            trampoline = trampoline.call();
        }
        return trampoline.done();
    }

    TailRecursion<T> call();

    T done();
}