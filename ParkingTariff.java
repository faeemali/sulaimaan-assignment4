
/**
 * A ParkingTariff represents the cost of a car park stay that 
 * falls within a given time period. e.g. R30 for 1-2 hours.
 *
 * @author Stephan Jamieson
 * @version 1/8/2019
 */
public class ParkingTariff {

    private final TimePeriod period;
    private final Money tariff;
    
    /**
     * Create a ParkingTariff for the given period.
     */
    public ParkingTariff(final TimePeriod period, final Money tariff) {
        this.period = period;
        this.tariff = tariff;
    }
    
    /**
     * Obtain the time period for which this parking tariff applies. 
     */
    public TimePeriod period() { return period; }
    
    /**
     * Obtain the tariff.
     */
    public Money tariff() { return tariff; }
    
    
    /**
     * Determine whether this parking tariff applies to a stay of the given duration.
     */
    public boolean appliesTo(final Duration durationOfStay) { return this.period.includes(durationOfStay); }
    
    /**
     * Obtain a string representation of the form "[time, time] : <cost>".
     */
    public String toString() { return period+" : "+tariff; }


}
