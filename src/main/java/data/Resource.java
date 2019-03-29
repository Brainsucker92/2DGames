package data;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

public abstract class Resource<T> {

    private Path path;
    private T data;

    private ClassLoader classLoader;

    public Resource(Path path) {
        this.path = path;

        this.classLoader = this.getClass().getClassLoader();
    }

    public void load(boolean force) {
        if (!isLoaded() || force) {
            InputStream inputStream = classLoader.getResourceAsStream(path.toString());
            Objects.requireNonNull(inputStream);
            data = convertData(inputStream);
        }
    }

    public void load() {
        load(false);
    }

    public abstract T convertData(InputStream inputStream);

    public T getData() {
        if (!isLoaded()) {
            throw new IllegalStateException("Data has not been loaded yet.");
        }
        return data;
    }

    public Path getPath() {
        return path;
    }

    public boolean isLoaded() {
        return data != null;
    }
}
