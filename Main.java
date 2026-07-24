import java.util.ArrayList;
import java.util.List;

class Car {
    String plate;
    String type;
    int speed;
    boolean seatbelt;

    Car(String plate, String type, int speed, boolean seatbelt) {
        this.plate = plate;
        this.type = type;
        this.speed = speed;
        this.seatbelt = seatbelt;
    }
}

class Violation {
    String name;
    int fine;

    Violation(String name, int fine) {
        this.name = name;
        this.fine = fine;
    }
}

class Fine {
    String plate;
    List<Violation> violations = new ArrayList<>();

    Fine(String plate) {
        this.plate = plate;
    }

    void addViolation(Violation v) {
        violations.add(v);
    }

    int totalFine() {
        int total = 0;

        for (Violation v : violations) {
            total = total + v.fine;
        }

        return total;
    }

    void print() {
        System.out.println("Car: " + plate);

        for (Violation v : violations) {
            System.out.println(v.name + " - " + v.fine + " EGP");
        }

        System.out.println("Total: " + totalFine() + " EGP");
        System.out.println();
    }
}

interface Rule {
    Violation check(Car car);
}

class PrivateCarRule implements Rule {

    public Violation check(Car car) {

        if (car.type.equals("Private") && car.speed > 80) {
            return new Violation("Speed violation", 300);
        }

        return null;
    }
}

class TruckRule implements Rule {

    public Violation check(Car car) {

        if (car.type.equals("Truck") && car.speed > 60) {
            return new Violation("Speed violation", 300);
        }

        return null;
    }
}

class SeatbeltRule implements Rule {

    public Violation check(Car car) {

        if (car.seatbelt == false) {
            return new Violation("No seatbelt", 100);
        }

        return null;
    }
}

class Radar {

    List<Rule> rules = new ArrayList<>();
    List<Fine> fines = new ArrayList<>();

    void addRule(Rule rule) {
        rules.add(rule);
    }

    void checkCar(Car car) {

        Fine fine = new Fine(car.plate);

        for (Rule rule : rules) {

            Violation v = rule.check(car);

            if (v != null) {
                fine.addViolation(v);
            }
        }

        if (fine.violations.size() > 0) {
            fine.print();
            fines.add(fine);
        }
    }

    void printAllFines() {

        System.out.println("All Fines:");

        for (Fine f : fines) {
            System.out.println(f.plate + " - " + f.totalFine() + " EGP");
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Radar radar = new Radar();

        radar.addRule(new PrivateCarRule());
        radar.addRule(new TruckRule());
        radar.addRule(new SeatbeltRule());

        Car car1 = new Car("ABC1234", "Private", 94, false);
        Car car2 = new Car("TRK9999", "Truck", 75, true);
        Car car3 = new Car("OK1111", "Private", 70, true);

        radar.checkCar(car1);
        radar.checkCar(car2);
        radar.checkCar(car3);

        radar.printAllFines();
    }
}