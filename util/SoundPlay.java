package util;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundPlay {

    public static void play(String file) {
        new Thread(() -> { // A separate thread so the Swing GUI doesn't freeze
            try {
                File soundFile = new File(file);

                if (!soundFile.exists()) {
                    System.out.println("Sound file not found: " + file);
                    return;
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();

                // Close the clip's resources once it's done playing
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        try {
                            audioStream.close();
                        } catch (IOException ignored) { }
                    }
                });

            } catch (UnsupportedAudioFileException e) {
                System.out.println("Unsupported audio format: " + file
                        + " (use .wav files)");
            } catch (LineUnavailableException e) {
                System.out.println("Audio line unavailable: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Could not read sound file: " + file);
            }
        }).start();
    }
}

//ONLY .wav files everyone!!