package utils.recursion;

public interface Recursion<T> {
    default void execute() {
        Recursion<T> trampoline = this;
        while (trampoline != null) {
            trampoline = trampoline.call();
        }
    }

    default T get() {
        Recursion<T> trampoline = this;
        while (trampoline.done() == null) {
            trampoline = trampoline.call();
        }
        return trampoline.done();
    }

    Recursion<T> call();

    T done();
}