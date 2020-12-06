package data.resources;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Represents a resource file within the resources folder.
 * The Path has to be specified relative to the resources folder of that project.
 * e. g. path/to/file.txt
 *
 * @param <T> The type of data this resource will produce after conversion
 */
public abstract class Resource<T> {

    private final Path path;
    private T data;

    private static final ClassLoader classLoader = Resource.class.getClassLoader();

    public Resource(Path path) {
        this.path = path;
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
