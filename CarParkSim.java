import java.util.Scanner;

/**
 * Write a description of class CarParkSimSolution here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CarParkSim {

    private CarParkSim() {
    }

    private static void addTimePeriod(TariffTable table, String unit1, int quantity1, String unit2, int quantity2, String amount, Currency currency) {
        Duration duration1 = new Duration(unit1, quantity1);
        Duration duration2 = new Duration(unit2, quantity2);
        TimePeriod period = new TimePeriod(duration1, duration2);

        Money money = new Money(amount, currency);
        table.addTariff(period, money);
    }

    public static void main(final String[] args) {
        final Scanner keyboard = new Scanner(System.in);
        final Clock clock = new Clock(new Time("00:00:00"));
        final Register register = new Register();

        Currency rand = new Currency("R", "ZAR", 100);
        TariffTable ttable = new TariffTable(10);

        TimePeriod timePeriod = new TimePeriod(new Duration("hour", 1), new Duration("hour", 2));

        addTimePeriod(ttable, "minutes", 0, "minutes", 30, "R10", rand);
        addTimePeriod(ttable, "minutes", 30, "hours", 1, "R15", rand);
        addTimePeriod(ttable, "hours", 1, "hours", 3, "R20", rand);
        addTimePeriod(ttable, "hours", 3, "hours", 4, "R30", rand);
        addTimePeriod(ttable, "hours", 4, "hours", 5, "R40", rand);
        addTimePeriod(ttable, "hours", 5, "hours", 6, "R50", rand);
        addTimePeriod(ttable, "hours", 6, "hours", 8, "R60", rand);
        addTimePeriod(ttable, "hours", 8, "hours", 10, "R70", rand);
        addTimePeriod(ttable, "hours", 10, "hours", 12, "R90", rand);
        addTimePeriod(ttable, "hours", 12, "days", 1, "R100", rand);

        System.out.println("Car Park Simulator");
        System.out.printf("The current time is %s.\n", clock.examine());
        System.out.println("Commands: tariffs, advance {minutes}, arrive, depart, quit.");
        System.out.print(">");

        String input = keyboard.next().toLowerCase();
        while (!input.equals("quit")) {
            if (input.equals("advance")) {
                clock.advance(new Duration("minute", keyboard.nextInt()));
                System.out.printf("The current time is %s.\n", clock.examine());
            } else if (input.equals("arrive")) {
                final Ticket ticket = new Ticket(clock.examine(), UIDGenerator.makeUID());
                register.add(ticket);
                System.out.printf("Ticket issued: %s.\n", ticket);
            } else if (input.equals("depart")) {
                final String ID = keyboard.next();
                if (!register.contains(ID)) {
                    System.out.println("Invalid ticket ID.");
                } else {
                    final Ticket ticket = register.retrieve(ID);
                    System.out.printf("Ticket details: %s.\n", ticket);
                    System.out.printf("Current time: %s.\n", clock.examine());
                    final Duration durationOfStay = ticket.age(clock.examine());
                    System.out.printf("Duration of stay: %s.\n", Duration.format(durationOfStay, "hour", "minute"));
                    String duration = Duration.format(durationOfStay, "hour", "minute");
                    System.out.println("Cost of stay : " + ttable.getTariff(durationOfStay) + ".");
                }
            } else if (input.equals("tariffs")) {
                System.out.println(ttable.toString());
            } else {
                System.out.println("That command is not recognised.");
                System.out.println("Commands: tariffs advance <minutes>, arrive, depart, quit.");
            }
            System.out.print(">");
            input = keyboard.next().toLowerCase();
        }
        System.out.println("Goodbye.");
    }
}