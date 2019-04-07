package data.grid;

import data.resources.Resource;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class MP3SoundResource extends Resource<Clip> {

    public MP3SoundResource(Path path) {
        super(path);
    }

    @Override
    public Clip convertData(InputStream inputStream) {
        try {
            // read the  file
            AudioInputStream rawInput = AudioSystem.getAudioInputStream(new ByteArrayInputStream(inputStream.readAllBytes()));

            // decode mp3
            AudioFormat baseFormat = rawInput.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, // Encoding to use
                    baseFormat.getSampleRate(),   // sample rate (same as base format)
                    16,               // sample size in bits (thx to Javazoom)
                    baseFormat.getChannels(),     // # of Channels
                    baseFormat.getChannels() * 2,   // Frame Size
                    baseFormat.getSampleRate(),   // Frame Rate
                    false                 // Big Endian
            );
            AudioInputStream decodedInput = AudioSystem.getAudioInputStream(decodedFormat, rawInput);
            Clip clip = AudioSystem.getClip();
            clip.open(decodedInput);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Unable to convert data.");
    }
}
