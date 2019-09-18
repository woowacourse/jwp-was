package utils.recursion;

public interface TailRecursion<T> {
    default void execute() {
        TailRecursion<T> trampoline = this;
        while (trampoline != null) {
            trampoline = trampoline.call();
        }
    }

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