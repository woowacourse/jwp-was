package webserver.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Objects;

public class NetworkInput implements Iterable<String> {
    private final BufferedReader reader;

    public NetworkInput(final InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public String readBody(int contentLength) throws IOException {
        char[] body = new char[contentLength];
        this.reader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    @Override
    public Iterator<String> iterator() {
        return new MyIterator(reader);
    }

    private static class MyIterator implements Iterator<String> {
        private static final String EMPTY = "";
        private BufferedReader reader;
        private String nextLine = EMPTY;

        MyIterator(final BufferedReader reader) {
            this.reader = reader;
            try {
                this.nextLine = this.reader.readLine();
            } catch (final IOException ignored) {}
        }

        @Override
        public boolean hasNext() {
            return Objects.nonNull(nextLine) && !EMPTY.equals(nextLine);
        }

        @Override
        public String next() {
            try {
                final String result = this.nextLine;
                this.nextLine = this.reader.readLine();
                return result;
            } catch (final IOException e) {
                return EMPTY;
            }
        }
    }
}
