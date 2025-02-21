import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SomUtils {

    public static Clip carregarSom(String caminho) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(caminho).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void tocarSom(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0); 
            clip.start();
        }
    }

    public static void tocarSomLoop(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0); 
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void pausarSom(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void continuarSom(Clip clip) {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }
    
    public static void pararSom(Clip clip) {
        if (clip != null) {
            clip.stop();
        }
    }
    public static void somDePause(Clip clip) {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }
    public static void somDePauseParado(Clip clip) {
        if (clip != null) {
            clip.stop();
        }
    }
    public static void ajustarVolume(Clip clip, float volume) {
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        }
    }
}