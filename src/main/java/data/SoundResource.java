package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class SoundResource extends Resource<AudioFileFormat> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundResource.class);

    public SoundResource(Path path) {
        super(path);
    }

    @Override
    public AudioFileFormat convertData(InputStream inputStream) {
        try {
            return AudioSystem.getAudioFileFormat(inputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            LOGGER.error("Unable to convert format.", e);
        }
        throw new IllegalArgumentException("Could not convert input stream to AudioFileFormat");
    }
}
