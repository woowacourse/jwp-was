package utils;

public class Trampoline<T>
{
    public T execute() {
        Trampoline<T> trampoline = this;
        while (trampoline.done() == null) {
            trampoline = trampoline.call();
        }
        return trampoline.done();
    }

    public Trampoline<T> call() {
        return null;
    }

    public T done() {
        return null;
    }
}