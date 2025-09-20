package game;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Centralized lightweight sound manager.
 * - Prefers JavaFX MediaPlayer for MP3 playback (loaded reflectively so it compiles without JavaFX).
 * - Falls back to JavaSound for WAV-only playback when JavaFX is unavailable.
 * If neither backend is available, sound calls become no-ops.
 */
public final class SoundManager {
    private static boolean fxAvailable = false;
    private static Object bgPlayer = null; // JavaFX MediaPlayer instance via reflection

    private SoundManager() { }

    static {
        // Try to start JavaFX Platform reflectively (ok to call once per JVM)
        try {
            Class<?> platform = Class.forName("javafx.application.Platform");
            Method isFxAppThread = platform.getMethod("isFxApplicationThread");
            boolean running = (boolean) isFxAppThread.invoke(null);
            if (!running) {
                Method startup = platform.getMethod("startup", Runnable.class);
                startup.invoke(null, new Runnable() { public void run() { } });
            }
            fxAvailable = true;
        } catch (Throwable t) {
            fxAvailable = false; // JavaFX not present
        }
    }

    /**
     * Play a short sound effect.
     *
     * @param path   file path (mp3 or wav)
     * @param volume 0.0..1.0 desired volume (relative)
     */
    public static void playEffect(String path, double volume) {
        if (fxAvailable && path.toLowerCase().endsWith(".mp3")) {
            playFxMp3(path, volume);
            return;
        }
        // WAV fallback
        if (path.toLowerCase().endsWith(".wav")) {
            playWav(path, volume);
        }
    }

    /**
     * Start looping background music.
     *
     * @param path   file path (mp3 or wav)
     * @param volume 0.0..1.0 volume
     */
    public static void loopMusic(String path, double volume) {
        stopMusic();
        if (fxAvailable && path.toLowerCase().endsWith(".mp3")) {
            bgPlayer = createMp3Player(path, volume);
            if (bgPlayer != null) {
                try {
                    Class<?> mpClass = Class.forName("javafx.scene.media.MediaPlayer");
                    // setCycleCount(MediaPlayer.INDEFINITE)
                    Field indefinite = mpClass.getField("INDEFINITE");
                    int val = indefinite.getInt(null);
                    Method setCycleCount = mpClass.getMethod("setCycleCount", int.class);
                    setCycleCount.invoke(bgPlayer, val);
                    mpClass.getMethod("play").invoke(bgPlayer);
                } catch (Throwable ignored) { }
            }
            return;
        }
        // WAV fallback
        if (path.toLowerCase().endsWith(".wav")) {
            // Looping via Clip
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                setClipVolume(clip, volume);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                bgPlayer = clip;
            } catch (Throwable ignored) { }
        }
    }

    /** Stop background music if any. */
    public static void stopMusic() {
        if (bgPlayer == null) {
            return;
        }
        try {
            if (fxAvailable) {
                Class<?> mpClass = Class.forName("javafx.scene.media.MediaPlayer");
                if (mpClass.isInstance(bgPlayer)) {
                    mpClass.getMethod("stop").invoke(bgPlayer);
                    try {
                        mpClass.getMethod("dispose").invoke(bgPlayer);
                    } catch (Throwable ignored) { }
                }
            }
            if (bgPlayer instanceof Clip) {
                Clip clip = (Clip) bgPlayer;
                clip.stop();
                clip.close();
            }
        } catch (Throwable ignored) { } finally {
            bgPlayer = null;
        }
    }

    // --- Internals ---
    private static void playFxMp3(String path, double volume) {
        Object player = createMp3Player(path, volume);
        if (player == null) {
            return;
        }
        try {
            Class<?> mpClass = Class.forName("javafx.scene.media.MediaPlayer");
            mpClass.getMethod("play").invoke(player);
            // Schedule disposal when finished
            Method setOnEndOfMedia = mpClass.getMethod("setOnEndOfMedia", Runnable.class);
            final Object pl = player;
            setOnEndOfMedia.invoke(player, new Runnable() {
                @Override public void run() {
                    try {
                        mpClass.getMethod("dispose").invoke(pl);
                    } catch (Throwable ignored) { }
                }
            });
        } catch (Throwable ignored) { }
    }

    private static Object createMp3Player(String path, double volume) {
        try {
            Class<?> mediaClass = Class.forName("javafx.scene.media.Media");
            Class<?> mpClass = Class.forName("javafx.scene.media.MediaPlayer");
            Constructor<?> mediaCtor = mediaClass.getConstructor(String.class);
            Object media = mediaCtor.newInstance(new File(path).toURI().toString());
            Constructor<?> mpCtor = mpClass.getConstructor(mediaClass);
            Object player = mpCtor.newInstance(media);
            Method setVolume = mpClass.getMethod("setVolume", double.class);
            setVolume.invoke(player, clamp01(volume));
            return player;
        } catch (Throwable t) {
            return null;
        }
    }

    private static void playWav(String path, double volume) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            setClipVolume(clip, volume);
            clip.start();
        } catch (Throwable ignored) { }
    }

    private static void setClipVolume(Clip clip, double volume) {
        try {
            FloatControl ctrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double vol = clamp01(volume);
            float dB = (float) (20.0 * Math.log10(Math.max(vol, 0.0001)));
            ctrl.setValue(dB);
        } catch (Throwable ignored) { }
    }

    private static double clamp01(double v) {
        if (v < 0.0) {
            return 0.0;
        }
        if (v > 1.0) {
            return 1.0;
        }
        return v;
    }
}
