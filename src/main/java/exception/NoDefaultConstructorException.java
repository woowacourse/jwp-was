package exception;

public class NoDefaultConstructorException extends RuntimeException {
    public <T> NoDefaultConstructorException(Class<T> clazz) {
        super(clazz.getName() + "클래스에 기본생성자가 필요합니다.");
    }
}
