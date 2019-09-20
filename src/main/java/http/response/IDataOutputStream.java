package http.response;

import java.io.DataOutput;
import java.io.IOException;

public interface IDataOutputStream extends DataOutput {
    void write(byte b[], int off, int len) throws IOException;
    void flush() throws IOException;
}
