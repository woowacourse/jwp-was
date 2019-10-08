package utils.io;

import java.util.Objects;

public final class FileExtension {
    final String name;

    public FileExtension(String filePathOrName) {
        this.name = (filePathOrName.contains("."))
                ? filePathOrName.substring(filePathOrName.lastIndexOf(".") + 1).toLowerCase()
                : "";
    }

    public boolean isNone() {
        return this.name.isEmpty();
    }

    public String get() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileExtension)) {
            return false;
        }
        final FileExtension rhs = (FileExtension) o;
        return Objects.equals(this.name, rhs.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
