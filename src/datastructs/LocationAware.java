package datastructs;

/**
 * Used by location-aware entries in an adaptable priority queue
 * 
 * @author Neil Dey
 *
 */
public interface LocationAware {
    /**
     * Gets the location in the priority queue
     * 
     * @return The location in the priority queue
     */
    int getLocation();

    /**
     * Sets the location in the priority queue
     * 
     * @param location
     *            The new location for the object in the priority queue
     */
    void setLocation(int location);
}
