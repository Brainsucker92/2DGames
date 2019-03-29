package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ImageResource extends Resource<BufferedImage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResource.class);

    public ImageResource(Path path) {
        super(path);
    }

    @Override
    public BufferedImage convertData(InputStream inputStream) {
        try {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            LOGGER.error("Failed to convert data.", e);
        }
        throw new IllegalArgumentException("Could not convert input stream successfully.");
    }
}
