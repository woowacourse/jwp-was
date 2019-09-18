package utils;

@FunctionalInterface
public interface TailCall<T>
{
    default T execute() {
        TailCall<T> trampoline = this;
        while (trampoline.done() == null) {
            trampoline = trampoline.call();
        }
        return trampoline.done();
    }

    TailCall<T> call();

    default T done() {
        return null;
    }
}