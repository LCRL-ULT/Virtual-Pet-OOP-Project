package ui;

import database.DatabaseConnection;
import java.util.Scanner;
import models.*;

public class PetGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n==================================");
        System.out.println("     WELCOME TO VIRTUAL PET");
        System.out.println("==================================");

        System.out.print("Welcome! What is your name? ");
        String ownerName = scanner.nextLine();
        
        Owner owner = new Owner(1, ownerName);
        System.out.println("\nNice to meet you, " + owner.getName() + "!");

        System.out.println("\n--- THE ADOPTION CENTER ---");
        boolean adopting = true;
        while (adopting) {
            System.out.println("\nWhat kind of pet would you like to adopt?");
            System.out.println("1. Dog");
            System.out.println("2. Cat");
            System.out.println("3. Dragon");
            System.out.println("4. Finish adopting and start the game");
            System.out.print("Choose an option (1-4): ");
            
            String choice = scanner.nextLine();

            if (choice.equals("4")) {
                if (owner.getPets().isEmpty()) {
                    System.out.println("You must adopt at least one pet before starting!");
                } else {
                    adopting = false;
                    System.out.println("\nAdoption complete! Let's go home.");
                }
            } else if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                System.out.print("What will you name this pet? ");
                String petName = scanner.nextLine();
                
                switch (choice) {
                    case "1": owner.adoptPet(new Dog(petName, "Mixed Breed")); break;
                    case "2": owner.adoptPet(new Cat(petName, true)); break;
                    case "3": owner.adoptPet(new Dragon(petName, 100)); break;
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        owner.getInventory().add(new FoodItem("Apple", 15));
        owner.getInventory().add(new FoodItem("Steak", 30));

        boolean playing = true;

        while (playing) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Feed a pet");
            System.out.println("2. Play with a pet");
            System.out.println("3. Put a pet to sleep");
            System.out.println("4. Make a pet speak");
            System.out.println("5. Use a pet's special ability");
            System.out.println("6. Visit the Food Store");
            System.out.println("7. View Inventory");
            System.out.println("8. Check all pet status");
            System.out.println("9. Adopt a new pet");
            System.out.println("10. Save and Quit");
            System.out.print(" Choose an option (1-10): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": feedMenu(owner, scanner); passTime(owner); break;
                case "2": interactMenu(owner, scanner, "play"); passTime(owner); break;
                case "3": interactMenu(owner, scanner, "sleep"); passTime(owner); break;
                case "4": interactMenu(owner, scanner, "speak"); passTime(owner); break;
                case "5": specialMenu(owner, scanner); passTime(owner); break;
                case "6": storeMenu(owner, scanner); break;
                case "7": viewInventory(owner); break;
                case "8": checkStatus(owner); break;
                case "9": adoptMenu(owner, scanner); break;
                case "10":
                    System.out.println("\n💾 Saving to database...");
                    DatabaseConnection.clearPets(); 
                    owner.saveToDatabase();
                    System.out.println("\nThanks for playing! Goodbye, " + owner.getName() + "! 👋");
                    playing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please type a number from 1 to 10.");
            }
        }
        scanner.close();
    }

    static void adoptMenu(Owner owner, Scanner scanner) {
        System.out.println("\n--- THE ADOPTION CENTER ---");
        System.out.println("What kind of pet would you like to adopt?");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Dragon");
        System.out.print("Choose an option (1-3) or press Enter to cancel: ");
        
        String choice = scanner.nextLine();
        if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
            System.out.print("What will you name this pet? ");
            String petName = scanner.nextLine();
            
            if (petName.trim().isEmpty()) {
                System.out.println("Pet needs a name! Adoption cancelled.");
                return;
            }

            switch (choice) {
                case "1": owner.adoptPet(new Dog(petName, "Mixed Breed")); break;
                case "2": owner.adoptPet(new Cat(petName, true)); break;
                case "3": owner.adoptPet(new Dragon(petName, 100)); break;
            }
            System.out.println(petName + " has officially joined the family!");
        }
    }

    static void feedMenu(Owner owner, Scanner scanner) {
        if (owner.getInventory().isEmpty()) {
            System.out.println("\nYour inventory is empty! Visit the Store (Option 6) to get food first.");
            return;
        }

        System.out.println("\nWhich pet do you want to feed?");
        listPets(owner);
        System.out.print("Enter pet number (or press Enter to cancel): ");
        int petIndex = readChoice(scanner, owner.getPets().size());
        
        if (petIndex != -1) {
            Pet pet = owner.getPets().get(petIndex);
            System.out.println("\nWhat would you like to feed " + pet.getName() + "?");
            for (int i = 0; i < owner.getInventory().size(); i++) {
                FoodItem food = owner.getInventory().get(i);
                System.out.println("  " + i + ". " + food.getName() + " (+" + food.getNutrition() + " fullness)");
            }
            System.out.print("Enter food number: ");
            int foodIndex = readChoice(scanner, owner.getInventory().size());

            if (foodIndex != -1) {
                FoodItem selectedFood = owner.getInventory().get(foodIndex);
                System.out.println("----------------------------------");
                pet.feed(selectedFood); 
                owner.getInventory().remove(foodIndex); 
                System.out.println("----------------------------------");
            }
        }
    }

    static void storeMenu(Owner owner, Scanner scanner) {
        System.out.println("\n==================================");
        System.out.println("          THE FOOD STORE ");
        System.out.println("==================================");
        System.out.println("  0. Apple (15 Nutrition)");
        System.out.println("  1. Fish (20 Nutrition)");
        System.out.println("  2. Steak (30 Nutrition)");
        System.out.println("  3. Dragon Fruit (50 Nutrition)");
        System.out.print("\nWhich item would you like to grab? (or press Enter to leave): ");
        
        int choice = readChoice(scanner, 4);
        if (choice != -1) {
            FoodItem newFood = null;
            switch (choice) {
                case 0: newFood = new FoodItem("Apple", 15); break;
                case 1: newFood = new FoodItem("Fish", 20); break;
                case 2: newFood = new FoodItem("Steak", 30); break;
                case 3: newFood = new FoodItem("Dragon Fruit", 50); break;
            }
            owner.getInventory().add(newFood);
            System.out.println("Added " + newFood.getName() + " to your inventory!");
        }
    }

    static void viewInventory(Owner owner) {
        System.out.println("\n--- 🎒 Your Inventory ---");
        if (owner.getInventory().isEmpty()) {
            System.out.println("  (Empty)");
        } else {
            for (FoodItem food : owner.getInventory()) {
                System.out.println("  - " + food.getName() + " (+" + food.getNutrition() + " Nutrition)");
            }
        }
    }

    static void interactMenu(Owner owner, Scanner scanner, String action) {
        System.out.println("\nWhich pet do you want to " + action + "?");
        listPets(owner);
        System.out.print("Enter pet number (or press Enter to cancel): ");
        int index = readChoice(scanner, owner.getPets().size());
        
        if (index != -1) {
            Pet pet = owner.getPets().get(index);
            System.out.println("----------------------------------");
            switch (action) {
                case "play": pet.play(); break;
                case "sleep": pet.sleep(); break;
                case "speak": pet.makeSound(); break;
            }
            System.out.println("----------------------------------");
        }
    }

    static void specialMenu(Owner owner, Scanner scanner) {
        System.out.println("\nWhich pet should use its special ability?");
        listPets(owner);
        System.out.print("Enter pet number (or press Enter to cancel): ");
        int index = readChoice(scanner, owner.getPets().size());
        if (index == -1) return;

        Pet pet = owner.getPets().get(index);
        System.out.println("----------------------------------");
        
        if (pet instanceof Dragon) ((Dragon) pet).breatheFire();
        else if (pet instanceof Dog) ((Dog) pet).fetch();
        else if (pet instanceof Cat) ((Cat) pet).scratch();
        
        System.out.println("----------------------------------");
    }

    static void checkStatus(Owner owner) {
        System.out.println("\n==================================");
        System.out.println("              STATUS ");
        System.out.println("==================================");
        for (Pet pet : owner.getPets()) {
            pet.showStatus();
        }
        System.out.println("==================================");
    }

    static void passTime(Owner owner) {
        System.out.println("\nTime passes...");
        for (Pet pet : owner.getPets()) {
            pet.increaseHunger(5);
            if (pet.getHunger() >= 75) {
                pet.decreaseHappiness(10); 
                pet.hungrySound();         
            } else {
                pet.decreaseHappiness(5);
            }
            if (pet.getHappiness() <= 20) {
                System.out.println("Warning: " + pet.getName() + " is very unhappy right now!");
            }
        }
    }

    static void listPets(Owner owner) {
        for (int i = 0; i < owner.getPets().size(); i++) {
            System.out.println("  " + i + ". " + owner.getPets().get(i).getName() 
                + " (" + owner.getPets().get(i).getClass().getSimpleName() + ")");
        }
    }

    static int readChoice(Scanner scanner, int maxLimit) {
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) return -1; 
        try {
            int index = Integer.parseInt(input);
            if (index >= 0 && index < maxLimit) return index;
        } catch (NumberFormatException e) { }
        System.out.println("Invalid choice.");
        return -1;
    }
}