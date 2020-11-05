package exception;

public class AttributeNotFoundException extends IllegalArgumentException {

    public AttributeNotFoundException(String attribute) {
        super("찾을 수 없는 Attribute입니다.attribute = " + attribute);
    }
}
