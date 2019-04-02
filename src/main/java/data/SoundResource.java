package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class SoundResource extends Resource<byte[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundResource.class);

    public SoundResource(Path path) {
        super(path);
    }

    @Override
    public byte[] convertData(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            while (inputStream.available() > 0) {
                outputStream.write(inputStream.read());
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read input stream", e);
        }

        return outputStream.toByteArray();
    }
}
