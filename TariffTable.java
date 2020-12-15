/**
* A TariffTable records parking tariffs for a pay-to-stay car park.
*
* @author Stephan Jamieson
* @version 1/8/2019
*/
public class TariffTable {

    private final ParkingTariff[] parkingTariffs;
    private int nextFree;
    
    /**
     * Create a TariffTable with the maximum number of entries.
    
     */
    public TariffTable(final int maxSize) { 
        parkingTariffs = new ParkingTariff[maxSize];
        nextFree = 0;
    }
    
    
    /**
     * Add the tariff for the given period to the table. The period must directly follow that for
     * the previous tariff entered.
     */
    public void addTariff(final TimePeriod period, final Money tariff) {
        final ParkingTariff parkingTariff = new ParkingTariff(period, tariff);
        
        if (nextFree==parkingTariffs.length) {
            throw new IllegalStateException("TariffTable is full.");
        }
        else {
            if (nextFree==0 || 
                parkingTariffs[nextFree-1].period().precedes(parkingTariff.period()) 
                && parkingTariffs[nextFree-1].period().adjacent(parkingTariff.period())) {
                parkingTariffs[nextFree] = parkingTariff;
                nextFree++;            
            }
            else {
                throw new IllegalArgumentException("Tariff periods must be adjacent.");
            }
        }
    }
        
    /**
     * Obtain the tariff for the given length of stay.
     */
    public Money getTariff(final Duration lengthOfStay) {
        for(int i=0; i<nextFree; i++) {
            if (parkingTariffs[i].appliesTo(lengthOfStay)) {
                return parkingTariffs[i].tariff();
            }
        }
        return null;
    }
    
    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer();
        int i=0;
        if (i<nextFree) {
            stringBuffer.append(parkingTariffs[i]);
            for(i=1; i<nextFree; i++) {
                stringBuffer.append("\n");
                stringBuffer.append(parkingTariffs[i]);
            }
        }
        return stringBuffer.toString();
    }
}
