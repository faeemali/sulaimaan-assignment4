
/**
 * A TimePeriod is a Duration range.  It has an inclusive lower bound, l, and an exclusive upper bound, u. 
 * A Duration, d, falls within the range if l<=d<u.
 * 
 * @author Stephan Jamieson
 * @version 1/8/19
 */
public class TimePeriod {
    
    private final Duration lowerBound;
    private final Duration upperBound;

    /**
     * Create a TimePeriod with the given inclusive lower bound and exclusive upper bound.
     */
    public TimePeriod(final Duration lowerBound, final Duration upperBound) {
        if (lowerBound.compareTo(upperBound)>=0) {
            throw new IllegalArgumentException("TimePeriod: Lower bound must be less than the upper bound.");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
        
    /**
     * Obtain the lower bound for this TimePeriod.
     */
    public Duration lowerBound() { return lowerBound; }
    
    /**
     * Obtain the upper bound for this TimePeriod.
     */
    public Duration upperBound() { return upperBound; }
    
    /**
     * Determine whether the given value falls within this TimePeriod i.e. whether upperBound()>value>=lowerBound(). 
     */
    public boolean includes(final Duration value) {
        return lowerBound.compareTo(value)<=0 && value.compareTo(upperBound)<0;
    }
        
    /**
     * Determine whether this TimePeriod precedes the other TimePeriod. i.e. this.upperBound()<=other.lowerBound();
     */
    public boolean precedes(final TimePeriod other) {
        return this.upperBound().compareTo(other.lowerBound())<=0;
    }
        
    /**
     * Determine whether this time period is directly adjacent to the other time period i.e. the upper bound of this
     * period equals the lower bound of the other, or vice-versa.
     */
    public boolean adjacent(final TimePeriod other) {
        return this.upperBound().compareTo(other.lowerBound())==0 || this.lowerBound().compareTo(other.upperBound())==0;
    }
    
    /**
     * Obtain a string representation of this TimePeriod of the form "[<hour> hours <minute> minutes...<hour> hours <minute>minutes]".
     */
    public String toString() {
        return String.format("[%s .. %s]", Duration.format(lowerBound(), "minute"), Duration.format(upperBound(), "minute"));
    }
    
    
}
