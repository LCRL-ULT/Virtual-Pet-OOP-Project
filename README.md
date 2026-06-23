# Virtual Pet OOP Project

A Java-based virtual pet game built with Object-Oriented Programming principles,
featuring a MySQL database for persistence, sound effects, and a Swing GUI.

**Course:** CAK2KAB4 - IF-48-INT (Object-Oriented Programming)
**Group:** Kelompok 2
**Members:**

- Carrol E. L - 103012450002
- Putu Argya D. - 103012440010
- Axella N. H. - 103012440018
- Tom H. - 103012400245
- Lakshay - 102022560014

---

## Features

- Multiple pet types (Dog, Cat, Dragon) using inheritance and polymorphism
- Each pet has unique sounds, feeding behavior, and a special ability
  (Dog fetches, Cat scratches, Dragon breathes fire)
- Multiple owners — each owner has their own set of pets
- Pet stats: hunger, happiness, and energy
- Realistic behavior: pets refuse to eat when full and refuse to play when tired
- Hunger decay over time
- Sound effects for each pet action (.wav files)
- MySQL database to save and load pets per owner
- Swing GUI to create owners, add pets, and interact with them

---

## Requirements

- Java JDK 17 or higher
- XAMPP (or any MySQL server)
- MySQL Connector/J (`mysql-connector-java-8.0.27.jar`, included in the project)

---

## Database Setup

1. Install and open XAMPP, then start the **MySQL** module.
2. Open phpMyAdmin at `http://localhost/phpmyadmin`.
3. Create a new database named **`virtualpet`**.
4. Open the `virtualpet` database, go to the **Import** tab, and import the
   provided `virtualpet.sql` file.

The default connection settings (in `database/DatabaseConnection.java`) are:

| Setting  | Value                                      |
|----------|--------------------------------------------|
| URL      | `jdbc:mysql://localhost:3306/virtualpet`   |
| Username | `root`                                     |
| Password | *(empty — XAMPP default)*                  |

If your MySQL uses a different username or password, update them in
`DatabaseConnection.java`.

---

## How to Run

Make sure MySQL is running, then from the project root folder:

**Compile:**
```
javac -cp ".;mysql-connector-java-8.0.27.jar" models\*.java database\*.java util\*.java ui\*.java
```

**Run the GUI version:**
```
java -cp ".;mysql-connector-java-8.0.27.jar" ui.PetGameGUI
```

**Run the console version:**
```
java -cp ".;mysql-connector-java-8.0.27.jar" ui.PetGame
```

> Note: Keep the `Sounds` folder in the project root so the sound effects work.

---

## Project Structure

```
Virtual-Pet-OOP-Project/
├── models/        Pet, Dog, Cat, Dragon, Owner, Feedable, FoodItem
├── database/      DatabaseConnection
├── ui/            PetGameGUI, PetGame
├── util/          SoundPlay
├── images/        .png pet illustrations
├── Sounds/        .wav sound effect files
├── mysql-connector-java-8.0.27.jar
└── virtualpet.sql
```

---

## OOP Concepts Demonstrated

- **Inheritance** — Dog, Cat, and Dragon extend the abstract Pet class
- **Polymorphism** — each pet overrides makeSound(), hungrySound(), and feed()
- **Abstraction** — Pet is an abstract class; Feedable is an interface
- **Encapsulation** — private/protected fields accessed through getters and setters
- **Casting** — used in the console menu to access pet-specific abilities
