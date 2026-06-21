import java.awt.*;
import javax.swing.*;

public class PetGameGUI extends JFrame {
    private Owner owner;
    private Pet selectedPet;

    private JComboBox<String> petSelector;
    private JLabel soundLabel;
    private JProgressBar hungerBar;
    private JProgressBar happinessBar;
    private JProgressBar energyBar;


    public PetGameGUI(Owner owner) {
        this.owner = owner;
        DatabaseConnection.initialize();
        
        setTitle("Virtual Pet Game");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Top: pick a pet ---
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Choose your pet:"));
        petSelector = new JComboBox<>();
        for (Pet pet : owner.getPets()) {
            petSelector.addItem(pet.getName());
        }
        petSelector.addActionListener(e -> selectPet());
        topPanel.add(petSelector);
        add(topPanel, BorderLayout.NORTH);

        // --- Center: the stat bars ---
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(4, 1, 5, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        soundLabel = new JLabel("Select a pet to begin", SwingConstants.CENTER);
        soundLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statsPanel.add(soundLabel);

        hungerBar = makeBar(Color.RED);
        happinessBar = makeBar(Color.GREEN);
        energyBar = makeBar(Color.BLUE);

        statsPanel.add(labeledBar("Hunger", hungerBar));
        statsPanel.add(labeledBar("Happiness", happinessBar));
        statsPanel.add(labeledBar("Energy", energyBar));
        add(statsPanel, BorderLayout.CENTER);

        // --- Bottom: action buttons ---
        JPanel buttonPanel = new JPanel();
        JButton feedBtn = new JButton("Feed");
        JButton playBtn = new JButton("Play");
        JButton sleepBtn = new JButton("Sleep");
        JButton soundBtn = new JButton("Make Sound");

        feedBtn.addActionListener(e -> { selectedPet.feed(); refresh(); });
        playBtn.addActionListener(e -> { selectedPet.play(); refresh(); });
        sleepBtn.addActionListener(e -> { selectedPet.sleep(); refresh(); });
        soundBtn.addActionListener(e -> makeSound());

        buttonPanel.add(feedBtn);
        buttonPanel.add(playBtn);
        buttonPanel.add(sleepBtn);
        buttonPanel.add(soundBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Start with the first pet selected
        selectPet();
        setVisible(true);
    }

    private JProgressBar makeBar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setForeground(color);
        return bar;
    }

    private JPanel labeledBar(String name, JProgressBar bar) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.add(new JLabel(name), BorderLayout.WEST);
        panel.add(bar, BorderLayout.CENTER);
        return panel;
    }

    private void selectPet() {
        int index = petSelector.getSelectedIndex();
        if (index >= 0) {
            selectedPet = owner.getPets().get(index);
            refresh();
        }
    }

    // Update the bars to match the selected pet's current stats
    private void refresh() {
        hungerBar.setValue(selectedPet.getHunger());
        happinessBar.setValue(selectedPet.getHappiness());
        energyBar.setValue(selectedPet.getEnergy());
    }

    private void makeSound() {
        // Polymorphism: each pet type prints its own sound to the console
        selectedPet.makeSound();
        soundLabel.setText(selectedPet.getName() + " made a sound! (check console)");
    }

   public static void main(String[] args) {
        Owner owner = new Owner(1, "Alex");
        owner.adoptPet(new Dog("Rex", "Labrador"));
        owner.adoptPet(new Cat("Whiskers", true));
        owner.adoptPet(new Dragon("Smaug", 100));

        // Save all pets to the real database
        owner.saveToDatabase();

        // Load them back to prove persistence works
        System.out.println("\n--- Loading pets from database ---");
        for (Pet p : DatabaseConnection.loadAllPets()) {
            System.out.println(p.getName() + " (" + p.getClass().getSimpleName() +
                ") - hunger: " + p.getHunger() +
                ", happiness: " + p.getHappiness() +
                ", energy: " + p.getEnergy());
        }

        // Launch the window
        SwingUtilities.invokeLater(() -> new PetGameGUI(owner));
    }
}