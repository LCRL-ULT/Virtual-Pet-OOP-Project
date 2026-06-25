package models;

import util.SoundPlay;

public class Dragon extends Pet {

    private int fireLevel;

    public Dragon(String name, int fireLevel) {
        super(name);
        this.fireLevel = fireLevel;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " lets out a mighty ROAAAR!");
        SoundPlay.play("Sounds/dragon-studio-epic-dragon-roar-364481.wav");
    }

     public void hungrySound(){
        System.out.println(getName() + " is hungry!");
        SoundPlay.play("Sounds/dragon-studio-dragon-growl-364483.wav");
    }

    @Override
    public void feed(FoodItem food) {
        if (isFull()) {
            System.out.println(getName() + " is full and refuses the feast.");
            return;
        }
        hunger = Math.max(0, hunger - food.getNutrition());
        happiness = Math.min(100, happiness + 10);
        System.out.println(getName() + " devours the " + food.getName() + " in one bite!");
        SoundPlay.play("Sounds/Dragon Bite Sound Effect - Needed Sound Effects (128k).wav");
    }

    // Dragon's own unique method
    public void breatheFire() {
        energy = Math.max(0, energy - 20);
        fireLevel += 5;
        System.out.println(getName() + " breathes fire! Fire level: " + fireLevel);
        SoundPlay.play("Sounds/dragon-studio-dragon-breathing-fire-364475-[AudioTrimmer.com].wav");
    }
}
