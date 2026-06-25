package models;

import util.SoundPlay;

public class Dog extends Pet {

    private String breed;

    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof! Woof!");
        SoundPlay.play("Sounds/mixkit-dog-barking-twice-1.wav");
    }

    @Override
     public void hungrySound(){
        System.out.println(getName() + " is hungry!");
        SoundPlay.play("Sounds/ElevenLabs_High-pitched_Chihuahua_whine,_urgent_and_pleading.wav");
    }


    @Override
    public void feed(FoodItem food) {
        if (isFull()) {
            System.out.println(getName() + " is full and doesn't want to eat.");
            return;
        }
        hunger = Math.max(0, hunger - food.getNutrition()); 
        happiness = Math.min(100, happiness + 5);
        System.out.println(getName() + " gobbles the " + food.getName() + " happily!");
        SoundPlay.play("Sounds/freesound_community-dog-eating-43938_[cut_5sec].wav");
    }

    public void fetch() {
        energy = Math.max(0, energy - 10);
        happiness = Math.min(100, happiness + 15);
        hunger = Math.max(0, hunger - 5);
        System.out.println(getName() + " fetches the ball!");
        SoundPlay.play("Sounds/yodguard-friendly-small-dog-bark-1-535470.wav");
    }
}
