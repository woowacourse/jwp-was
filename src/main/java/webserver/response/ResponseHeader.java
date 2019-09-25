package webserver.response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ResponseHeader implements Map<String, Object> {
    private Map<String, Object> header;

    public ResponseHeader() {
        header = new HashMap<>();
    }

    public static ResponseHeader error(int length) {
        ResponseHeader header = new ResponseHeader();
        header.setContentLegthAndType(length, "text/html;charset=utf-8");
        return header;
    }

    public void setContentLegthAndType(int length, String type) {
        header.put("Content-Length", length);
        header.put("Content-Type", type);
    }

    public void setLocation(String location) {
        header.put("Location", location);
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return header.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return header.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return header.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        header.putAll(m);
    }

    @Override
    public void clear() {
        header.clear();
    }

    @Override
    public Set<String> keySet() {
        return header.keySet();
    }

    @Override
    public Collection<Object> values() {
        return header.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return header.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseHeader that = (ResponseHeader) o;

        return Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return header != null ? header.hashCode() : 0;
    }
}
