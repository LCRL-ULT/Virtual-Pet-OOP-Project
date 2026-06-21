package models;

import database.DatabaseConnection;
import java.util.ArrayList;

public class Owner {
    private int ownerId;
    private String name;
    private ArrayList<Pet> pets;

    public Owner(int ownerId, String name) {
        this.ownerId = ownerId;
        this.name = name;
        this.pets = new ArrayList<>();
    }

    public void adoptPet(Pet pet) {
        pets.add(pet);
        System.out.println(name + " adopted " + pet.getName() + "!");
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void feedAllPets() {
        System.out.println("\n--- " + name + " is feeding all pets ---");
        for (Pet pet : pets) {
            pet.feed();
        }
    }

    public void rollCall() {
        System.out.println("\n--- Roll call! ---");
        for (Pet pet : pets) {
            pet.makeSound();
        }
    }

public void saveToDatabase() {
        System.out.println("\n--- Saving " + name + "'s pets ---");
        DatabaseConnection.initialize();
        for (Pet pet : pets) {
            DatabaseConnection.savePet(pet, name);
        }
    }

    public String getName() {
        return name;
    }
}