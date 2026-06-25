package models;

public abstract class Pet implements Feedable {

    private String name;
    protected int hunger;
    protected int happiness;
    protected int energy;

    public Pet(String name) {
        this.name = name;
        this.hunger = 50;
        this.happiness = 50;
        this.energy = 50;
    }

    // Abstract method — every pet MUST define its own sound
    public abstract void makeSound();

    public abstract void hungrySound();


    // Shared behavior all pets inherit
    public int getEnergy() {
        return energy;
    }

    public boolean isFull() {
        return hunger <= 0;
    }

    public void play() {
        if (energy < 20) {
            System.out.println(getName() + " is too tired to play right now.");
            return;
        }
        if (hunger > 75) {
            System.out.println(getName() + " is too hungry to play right now");
            return; 
        }
        happiness += 10;
        energy -= 10;
        System.out.println(getName() + " is having fun playing!");
        hunger += 10; 
    }

    public void sleep() {
        energy += 20;
        System.out.println(name + " is sleeping. Energy is now " + energy);
    }

    public void showStatus() {
        System.out.println(getName() + " | Hunger: " + hunger + " | Happiness: " + happiness + " | Energy: " + energy);
    }

    // Encapsulation: getters and setters for the private field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    // To limit the value of hunger to 100 
    public void increaseHunger(int amount){
        hunger += amount; 
        if (hunger > 100){
        hunger = 100; 
        }
    }

    //Limit the value of happiness to 0 
    public void decreaseHappiness(int amount){
        happiness -= amount; 
        if (happiness < 0){
            happiness = 0; 
        }
    }
}

