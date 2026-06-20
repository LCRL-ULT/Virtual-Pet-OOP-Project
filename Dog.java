public class Dog extends Pet {
    private String breed;

    public Dog(String name, String breed) {
        super(name);          // calls the Pet constructor
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof! Woof!");
    }

    @Override
    public void feed() {
        hunger -= 15;
        happiness += 10;
        System.out.println(getName() + " gobbles the food happily!");
    }

    // Dog's own unique method
    public void fetch() {
        energy -= 10;
        happiness += 15;
        System.out.println(getName() + " fetches the ball!");
    }
}