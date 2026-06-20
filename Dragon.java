public class Dragon extends Pet {
    private int fireLevel;

    public Dragon(String name, int fireLevel) {
        super(name);
        this.fireLevel = fireLevel;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " lets out a mighty ROAAAR!");
    }

    @Override
    public void feed() {
        hunger -= 25;
        happiness += 20;
        System.out.println(getName() + " devours an entire feast!");
    }

    // Dragon's own unique method
    public void breatheFire() {
        energy -= 20;
        fireLevel += 5;
        System.out.println(getName() + " breathes fire! Fire level: " + fireLevel);
    }
}