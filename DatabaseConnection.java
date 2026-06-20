public class DatabaseConnection {
    // 'static final' = constants shared by the whole class (the - underline in your class diagram)
    private static final String URL = "jdbc:simulated://localhost:3306/virtualpet";
    private static final String USER = "admin";
    private static final String PASSWORD = "password";

    // Simulates opening a connection
    public static void getConnection() {
        System.out.println("[DB] Connected to " + URL + " as " + USER);
    }

    // Simulates saving data
    public static void save(String data) {
        System.out.println("[DB] Saved: " + data);
    }
}