package utils.recursion;

@FunctionalInterface
public interface Procedure extends TailRecursion<Void> {
    static Procedure exit() {
        return () -> null;
    }

    @Override
    default Void done() {
        return null;
    }
}