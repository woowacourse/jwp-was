package webserver.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Objects;

public class NetworkInput implements Iterable<String> {
    private final BufferedReader bufferedReader;

    public NetworkInput(final InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    @Override
    public Iterator<String> iterator() {
        try {
            return new MyIterator(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
            return iterator();
        }
    }

    private static class MyIterator implements Iterator<String> {
        private BufferedReader bufferedReader;
        private String nextLine;

        MyIterator(final BufferedReader bufferedReader) throws IOException {
            this.bufferedReader = bufferedReader;
            this.nextLine = bufferedReader.readLine();
        }

        @Override
        public boolean hasNext() {
            return Objects.nonNull(nextLine) && !"".equals(nextLine);
        }

        @Override
        public String next() {
            try {
                final String result = this.nextLine;
                this.nextLine = bufferedReader.readLine();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}
