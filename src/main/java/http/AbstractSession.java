package http;

import http.response.SessionRemover;

public abstract class AbstractSession implements Session {
    private final String id;
    private final SessionRemover remover;

    private boolean invalidated = false;

    AbstractSession(String id, SessionRemover remover) {
        this.id = id;
        this.remover = remover;
    }

    @Override
    public String getId() {
        assertValidation();

        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        assertValidation();

        _setAttribute(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        assertValidation();

        return _getAttribute(name);
    }

    @Override
    public void removeAttribute(String name) {
        assertValidation();

        _removeAttribute(name);
    }

    @Override
    public void invalidate() {
        assertValidation();

        invalidated = true;

        remover.remove(id);
    }

    private void assertValidation() {
        if (invalidated) {
            throw new IllegalSessionStateException();
        }
    }

    abstract void _setAttribute(String name, Object value);

    abstract Object _getAttribute(String name);

    abstract void _removeAttribute(String name);
}
