package interfaces;

/**
 * Listener for hit events on game objects.
 */
public interface HitListener {
    /**
     * Called when a hit occurs on the observed object.
     *
     * @param beingHit the object that was hit
     */
    void hitEvent(Object beingHit);
}