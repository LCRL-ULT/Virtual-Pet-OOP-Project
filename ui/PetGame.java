package ui;

import database.DatabaseConnection;
import java.util.Scanner;
import models.*;

public class PetGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Set up the owner and pets
        Owner owner = new Owner(1, "Alex");
        owner.adoptPet(new Dog("Rex", "Blue Heeler"));
        owner.adoptPet(new Cat("Whiskers", true));
        owner.adoptPet(new Dragon("Smaug", 100));

        boolean playing = true;

        while (playing) {
            // Show the menu
            System.out.println("\n========================");
            System.out.println("   VIRTUAL PET GAME");
            System.out.println("========================");
            System.out.println("1. Feed a pet");
            System.out.println("2. Make all pets speak");
            System.out.println("3. Check all pet status");
            System.out.println("4. Special ability");
            System.out.println("5. Save and quit");
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    feedMenu(owner, scanner);
                    break;
                case "2":
                    owner.rollCall();
                    break;
                case "3":
                    checkStatus(owner);
                    break;
                case "4":
                    specialMenu(owner, scanner);
                    break;
                case "5":
                    DatabaseConnection.clearPets();
                    owner.saveToDatabase();
                    System.out.println("\nThanks for playing! Goodbye!");
                    playing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please type a number from 1 to 5.");
            }
        }

        scanner.close();
    }

    // Lets the player pick which pet to feed
    static void feedMenu(Owner owner, Scanner scanner) {
        System.out.println("\nWhich pet do you want to feed?");
        listPets(owner);
        System.out.print("Enter pet number: ");
        int index = readPetNumber(owner, scanner);
        if (index != -1) {
            owner.getPets().get(index).feed();
        }
    }

    // Lets the player use a pet's unique ability — this is where CASTING happens
    static void specialMenu(Owner owner, Scanner scanner) {
        System.out.println("\nWhich pet should use its special ability?");
        listPets(owner);
        System.out.print("Enter pet number: ");
        int index = readPetNumber(owner, scanner);
        if (index == -1) return;

        Pet pet = owner.getPets().get(index);

        // Casting: a Pet reference can't call breatheFire()/fetch()/scratch()
        // directly, so we check the real type and cast to unlock it.
        if (pet instanceof Dragon) {
            ((Dragon) pet).breatheFire();
        } else if (pet instanceof Dog) {
            ((Dog) pet).fetch();
        } else if (pet instanceof Cat) {
            ((Cat) pet).scratch();
        }
    }

    // Shows every pet's stats
    static void checkStatus(Owner owner) {
        System.out.println("\n--- Pet Status ---");
        for (Pet pet : owner.getPets()) {
            pet.showStatus();
        }
    }

    // Prints a numbered list of pets
    static void listPets(Owner owner) {
        for (int i = 0; i < owner.getPets().size(); i++) {
            System.out.println(i + ". " + owner.getPets().get(i).getName());
        }
    }

    // Reads and validates the pet number the player types
    static int readPetNumber(Owner owner, Scanner scanner) {
        String input = scanner.nextLine();
        try {
            int index = Integer.parseInt(input);
            if (index >= 0 && index < owner.getPets().size()) {
                return index;
            }
        } catch (NumberFormatException e) {
            // not a number — fall through
        }
        System.out.println("Invalid pet number.");
        return -1;
    }
}