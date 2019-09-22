package utils.recursion;

@FunctionalInterface
public interface RecursiveProcedure {
    static RecursiveProcedure exit() {
        return () -> null;
    }

    default void execute() {
        RecursiveProcedure trampoline = this;
        while (trampoline != null) {
            trampoline = trampoline.repeat();
        }
    }

    RecursiveProcedure repeat();
}