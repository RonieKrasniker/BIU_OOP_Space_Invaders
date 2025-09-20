package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Utility for loading and caching images from disk or classpath.
 */
public class ImageManager {
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static final Map<String, Image> SCALED_CACHE = new HashMap<>();

    /**
     * Load image from path (relative to working dir or classpath) with caching.
     *
     * @param path path like "media/sprites/spaceship.png"
     * @return Image or null if failed
     */
    public static Image get(String path) {
        if (path == null) {
            return null;
        }
        if (CACHE.containsKey(path)) {
            return CACHE.get(path);
        }
        // Try file system first
        try {
            BufferedImage img = ImageIO.read(new File(path));
            if (img != null) {
                CACHE.put(path, img);
                return img;
            }
        } catch (IOException e) {
            // ignore and try classpath
        }
        // Try classpath
        try (InputStream is = ImageManager.class.getClassLoader().getResourceAsStream(path)) {
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                CACHE.put(path, img);
                return img;
            }
        } catch (IOException e) {
            // ignore
        }
        return null;
    }

    /**
     * Get a scaled Image for the given path and target size (uses cache).
     *
     * @param path  image path
     * @param width target width in pixels
     * @param height target height in pixels
     * @return scaled Image or null if source not found
     */
    public static Image getScaled(String path, int width, int height) {
        String key = path + "#" + width + "x" + height;
        if (SCALED_CACHE.containsKey(key)) {
            return SCALED_CACHE.get(key);
        }
        Image base = get(path);
        if (base == null) {
            return null;
        }
        // Draw into a buffered image of the exact target size to ensure consistent rendering
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = resized.createGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(base, 0, 0, width, height, null);
        g2.dispose();
        SCALED_CACHE.put(key, resized);
        return resized;
    }
}
