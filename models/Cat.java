package models;

import util.SoundPlay;

public class Cat extends Pet {

    private boolean isIndoor;

    public Cat(String name, boolean isIndoor) {
        super(name);
        this.isIndoor = isIndoor;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Meow~");
        SoundPlay.play("Sounds/mixkit-sweet-kitty-meow-93.wav");
    }

    @Override
    public void hungrySound(){
        System.out.println(getName() + " is hungry!");
        SoundPlay.play("Sounds/mixkit-cartoon-kitty-begging-meow-92.wav");
    }

    @Override
    public void feed(FoodItem food) {
        if (isFull()) {
            System.out.println(getName() + " turns its nose up — too full to eat.");
            return;
        }
        hunger = Math.max(0, hunger - food.getNutrition()); 
        happiness = Math.min(100, happiness + 5);
        System.out.println(getName() + " nibbles delicately at the " + food.getName() + ".");
        SoundPlay.play("Sounds/freesound_community-cat-eating-81278_[cut_4sec].wav");
    }

    public void scratch() {
        happiness = Math.min(100, happiness + 10);
        System.out.println(getName() + " scratches the post contentedly.");
        SoundPlay.play("Sounds/dragon-studio-cute-cat-meow-472372.wav");
    }
}
