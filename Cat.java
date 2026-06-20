public class Cat extends Pet {
    private boolean isIndoor;

    public Cat(String name, boolean isIndoor) {
        super(name);
        this.isIndoor = isIndoor;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Meow~");
    }

    @Override
    public void feed() {
        hunger -= 10;
        happiness += 5;
        System.out.println(getName() + " nibbles delicately at the food.");
    }

    // Cat's own unique method
    public void scratch() {
        happiness += 10;
        System.out.println(getName() + " scratches the post contentedly.");
    }
}