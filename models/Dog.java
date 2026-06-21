package models;

import util.SoundPlay;

public class Dog extends Pet {

    private String breed;

    public Dog(String name, String breed) {
        super(name);          // calls the Pet constructor
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof! Woof!");
        SoundPlay.play("Sounds\\mixkit-dog-barking-twice-1.wav");
    }

    @Override
     public void hungrySound(){
        System.out.println(getName() + "is hungry!");
        SoundPlay.play("Sounds/mixkit-dog-sad-whimper-467.wav");
    }


    @Override
    public void feed() {
        if (isFull()) {
            System.out.println(getName() + " is full and doesn't want to eat.");
            return;
        }
        hunger -= 15;
        happiness += 10;
        System.out.println(getName() + " gobbles the food happily!");
        SoundPlay.play("Sounds/freesound_community-dog-eating-43938_[cut_5sec].wav");
    }

    // Dog's own unique method
    public void fetch() {
        energy -= 10;
        happiness += 15;
        System.out.println(getName() + " fetches the ball!");
    }
}
