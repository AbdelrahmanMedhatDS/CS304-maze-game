package mazegame.logic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//created by wael
public class musicPlayer {

    private Clip backgroundClip;

    public void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void playSoundEffect(String filePath) {
        try {
            // Create a File object for the sound file
            File soundFile = new File(filePath);

            // Create an AudioInputStream object
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // Create a Clip object
            Clip clip = AudioSystem.getClip();

            // Open the Clip object with the AudioInputStream
            clip.open(audioInputStream);

            // Start playing the sound effect
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing sound effect: " + e.getMessage());
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }
}
