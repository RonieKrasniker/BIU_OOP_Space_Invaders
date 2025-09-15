package geometry;

/**
 * A rectangle defined by its upper-left corner coordinates and dimensions.
 */
public class Rectangle {
    private double x;
    private double y;
    private double width;
    private double height;
    
    /**
     * Create a new rectangle with the specified location and dimensions.
     * @param x the x coordinate of the upper-left corner
     * @param y the y coordinate of the upper-left corner  
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Get the x coordinate of the upper-left corner.
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }
    
    /**
     * Get the y coordinate of the upper-left corner.
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }
    
    /**
     * Get the width of the rectangle.
     * @return the width
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Get the height of the rectangle.
     * @return the height
     */
    public double getHeight() {
        return height;
    }
    
    /**
     * Check if this rectangle intersects with another rectangle.
     * @param other the other rectangle
     * @return true if they intersect, false otherwise
     */
    public boolean intersects(Rectangle other) {
        return !(other.getX() >= this.x + this.width || 
                other.getX() + other.getWidth() <= this.x ||
                other.getY() >= this.y + this.height ||
                other.getY() + other.getHeight() <= this.y);
    }
}