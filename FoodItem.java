public class FoodItem {
    private String name;
    private int nutrition;

    public FoodItem(String name, int nutrition) {
        this.name = name;
        this.nutrition = nutrition;
    }

    // Encapsulation: getters for the private fields
    public String getName() {
        return name;
    }

    public int getNutrition() {
        return nutrition;
    }
}