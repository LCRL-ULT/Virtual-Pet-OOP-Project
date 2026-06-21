package database;

import models.Pet;
import models.Dog;
import models.Cat;
import models.Dragon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/virtualpet";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("[DB] Connected to MySQL: " + URL);
            return conn;
        } catch (SQLException e) {
            System.out.println("[DB] Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS pets (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "name VARCHAR(100) NOT NULL, " +
                     "type VARCHAR(50) NOT NULL, " +
                     "hunger INT, " +
                     "happiness INT, " +
                     "energy INT, " +
                     "owner VARCHAR(100))";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("[DB] Table ready.");
        } catch (SQLException e) {
            System.out.println("[DB] Error creating table: " + e.getMessage());
        }
    }

    public static void savePet(Pet pet, String ownerName) {
        String sql = "INSERT INTO pets(name, type, hunger, happiness, energy, owner) " +
                     "VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pet.getName());
            ps.setString(2, pet.getClass().getSimpleName());
            ps.setInt(3, pet.getHunger());
            ps.setInt(4, pet.getHappiness());
            ps.setInt(5, pet.getEnergy());
            ps.setString(6, ownerName);
            ps.executeUpdate();
            System.out.println("[DB] Saved: " + pet.getName());
        } catch (SQLException e) {
            System.out.println("[DB] Error saving pet: " + e.getMessage());
        }
    }

    public static void clearPets() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM pets");
        } catch (SQLException e) {
            System.out.println("[DB] Error clearing: " + e.getMessage());
        }
    }

    public static void save(String data) {
        System.out.println("[DB] (legacy) " + data);
    }

    public static ArrayList<Pet> loadAllPets() {
        ArrayList<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("type");
                String name = rs.getString("name");

                Pet pet = null;
                switch (type) {
                    case "Dog":    pet = new Dog(name, "Unknown"); break;
                    case "Cat":    pet = new Cat(name, true);      break;
                    case "Dragon": pet = new Dragon(name, 100);    break;
                }

                if (pet != null) {
                    pet.setHunger(rs.getInt("hunger"));
                    pet.setHappiness(rs.getInt("happiness"));
                    pet.setEnergy(rs.getInt("energy"));
                    pets.add(pet);
                }
            }
        } catch (SQLException e) {
            System.out.println("[DB] Error loading pets: " + e.getMessage());
        }
        return pets;
    }
}