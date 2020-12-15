import java.util.Scanner;
/**
 * Write a description of class CarParkSimSolution here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CarParkSim {

    private CarParkSim() {}


    public static void main(final String[] args) {
        final Scanner keyboard = new Scanner(System.in);
        final Clock clock = new Clock(new Time("00:00:00"));
        final Register register = new Register();

        Currency rand = new Currency("R", "ZAR", 100);
        TariffTable ttable = new TariffTable(10);

        new TimePeriod(new Duration("hour", 1), new Duration("hour", 2));

        ttable.addTariff(new TimePeriod(new Duration("minutes", 0), new Duration("minutes", 30)), new Money("R10", rand));
        ttable.addTariff(new TimePeriod(new Duration("minutes", 30), new Duration("hours", 1)), new Money("R15", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 1), new Duration("hours", 3)), new Money("R20", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 3), new Duration("hours", 4)), new Money("R30", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 4), new Duration("hours", 5)), new Money("R40", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 5), new Duration("hours", 6)), new Money("R50", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 6), new Duration("hours", 8)), new Money("R60", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 8), new Duration("hours", 10)), new Money("R70", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 10), new Duration("hours", 12)), new Money("R90", rand));
        ttable.addTariff(new TimePeriod(new Duration("hours", 12), new Duration("days", 1)), new Money("R100", rand));

        System.out.println("Car Park Simulator");
        System.out.printf("The current time is %s.\n", clock.examine());
        System.out.println("Commands: tariffs, advance {minutes}, arrive, depart, quit.");
        System.out.print(">");
        String input = keyboard.next().toLowerCase();
        while (!input.equals("quit")) {
            if (input.equals("advance")) {
                clock.advance(new Duration("minute", keyboard.nextInt()));
                System.out.printf("The current time is %s.\n", clock.examine());
            }
            else if (input.equals("arrive")) {
                final Ticket ticket = new Ticket(clock.examine(), UIDGenerator.makeUID());
                register.add(ticket);
                System.out.printf("Ticket issued: %s.\n", ticket);
            }
            else if (input.equals("depart")) {
                final String ID = keyboard.next();
                if (!register.contains(ID)) {
                    System.out.println("Invalid ticket ID.");
                }
                else {
                    final Ticket ticket = register.retrieve(ID);
                    System.out.printf("Ticket details: %s.\n", ticket);
                    System.out.printf("Current time: %s.\n", clock.examine());
                    final Duration durationOfStay = ticket.age(clock.examine());
                    System.out.printf("Duration of stay: %s.\n", Duration.format(durationOfStay, "hour", "minute"));
                    String duration = Duration.format(durationOfStay, "hour", "minute");
                    System.out.printf("Duration of stay: %s.\n", duration);
                    System.out.println("Cost of stay : " + ttable.getTariff(durationOfStay) + ".");
                }
            }
            else if (input.equals("tariffs")) {
                System.out.println(ttable.toString());
            }
            else {
                System.out.println("That command is not recognised.");
                System.out.println("Commands: tariffs advance <minutes>, arrive, depart, quit.");
            }
            System.out.print(">");
            input = keyboard.next().toLowerCase();
        }
        System.out.println("Goodbye.");
    }
}