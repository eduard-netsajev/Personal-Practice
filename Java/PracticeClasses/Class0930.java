import java.util.ArrayList;

public class Class0930 {
    public static void main(String[] args) {

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Car("Audi", 240, 5, 4));
        vehicles.add(new Car("Bugatti", 407, 2, 2));
        vehicles.add(new Plane("boeing", 842, 200, 13000));
        vehicles.add(new Motorcycle("Suzuki", 186, 66.6));

        for (Vehicle v : vehicles) {
            if (v instanceof Plane) {
                ((Plane) v).takeOff();
            }
            v.move();
            v.print();
            System.out.println();
        }
    }
}

abstract class Vehicle {
    String name;
    int maxSpeed; // in kilometers per hour

    Vehicle(String name, int maxSpeed) {
        this.name = name;
        this.maxSpeed = maxSpeed;
    }

    abstract void move();

    abstract void print();
}

class Car extends Vehicle {
    private int seats;
    private int doors;

    Car(String name, int maxSpeed) {
        super(name, maxSpeed);
        seats = 4;
        doors = 4;
    }

    Car(String name, int maxSpeed, int seatsNumber, int doorsNumber) {
        super(name, maxSpeed);
        seats = seatsNumber;
        doors = doorsNumber;
    }

    public void move(){
        System.out.printf("a car has a max speed of %d km/h\n", maxSpeed);
    }
    public void print(){
        System.out.println("Car:");
        System.out.printf("name: %s\nmax speed: %d km/h\n", name, maxSpeed);
        System.out.printf("doors: %d\nseats: %d\n", doors, seats);
    }
}

class Motorcycle extends Vehicle {
    private double maxPower;

    Motorcycle(String name, int maxSpeed, double maxPower) {
        super(name, maxSpeed);
        this.maxPower = maxPower;
    }

    public void move(){
        System.out.printf("a motorcycle has a max speed of %d km/h\n", maxSpeed);
    }
    public void print(){
        System.out.println("Motorcycle:");
        System.out.printf("name: %s\nmax speed: %d km/h\n", name, maxSpeed);
        System.out.printf("max power: %.1f hp\n", maxPower);
    }
}

class Plane extends Vehicle {
    private int seats;
    private int maxAltitude; // in meters

    Plane(String name, int maxSpeed, int seatsNumber, int maxAltitude) {
        super(name, maxSpeed);
        seats = seatsNumber;
        this.maxAltitude = maxAltitude;
    }

    public void move(){
        System.out.printf("a plane has a max speed of %d km/h\n", maxSpeed);
    }
    public void print(){
        System.out.println("Plane:");
        System.out.printf("name: %s\nmax speed: %d km/h\n", name, maxSpeed);
        System.out.printf("seats: %d\nmax altitude: %d m\n",
                seats, maxAltitude);
    }
    public void takeOff(){
        System.out.printf("Taking off to %d m.\n", maxAltitude);
    }
}

