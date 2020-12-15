import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * A Register stores a collection of Tickets. Tickets may be retrieved given their ID.
 *
 * @author Stephan Jamieson
 * @version 15/7/2019
 */
public class Register {

    private Ticket[] tickets;
    private int numTickets;
    
    private interface Criterion {
        boolean match(final Ticket t);
    }
    
    private int find(final Criterion c) {
        for(int i=0; i<tickets.length; i++) {
            if (c.match(tickets[i])) {
                return i;
            }
        }
        return -1;
    }
            
    private int findTicket(final String ID) {
        return find(new Criterion() {
            public boolean match(final Ticket t) {
                return t!=null && t.ID().equals(ID);
            }
        });
    }
    
    private int findSpace() {
        return find(new Criterion() {
            public boolean match(final Ticket t) {
                return t==null;
            }
        });
    }
    
    /**
     * Create a new Register object.
     */
    public Register() {
        Monitor.log("Register:Register()\n");
        this.tickets = new Ticket[100];
        this.numTickets = 0;
    }
    
    
    /**
     * Store the given Ticket in the register.
     */
    public void add(final Ticket ticket) {
        if (numTickets==tickets.length) {
            // Full!
            tickets = Arrays.copyOf(tickets, tickets.length+100, tickets.getClass());
            this.tickets[numTickets]=ticket;
        }
        else {
            this.tickets[findSpace()]=ticket;
        }
        numTickets++;
    }
    
    /**
     * Determine whether the register contains the Ticket with the given ID.
     */
    public boolean contains(final String ticketID) {
        return findTicket(ticketID)>-1;
    }
    
    /**
     * Retrieve the Ticket with the given ID. 
     */
    public Ticket retrieve(final String ticketID) {
        int index = findTicket(ticketID);
        if (index<0) {
            throw new IllegalArgumentException("Register:retrieve("+ticketID+"): invalid ticket ID.");
        }
        else {
            return tickets[index];
        }
    }

}
