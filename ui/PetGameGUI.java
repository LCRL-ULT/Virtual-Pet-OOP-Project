package ui;

import database.DatabaseConnection;
import java.awt.*;
import javax.swing.*;
import models.*;

public class PetGameGUI extends JFrame {

    private Owner owner;
    private Pet selectedPet;

    private JComboBox<String> petSelector;
    private JLabel soundLabel;
    private JLabel imageLabel;
    private JProgressBar hungerBar;
    private JProgressBar happinessBar;
    private JProgressBar energyBar;

    private static final int hungry_threshold = 75;

    // Modern UI Colors
    private final Color COLOR_BG = new Color(245, 247, 250);
    private final Color COLOR_PANEL = Color.WHITE;
    private final Color COLOR_TEXT = new Color(44, 62, 80);
    private final Font FONT_MAIN = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    public PetGameGUI(Owner owner) {
        this.owner = owner;
        DatabaseConnection.initialize();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Virtual Pet Game");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Save the current pet stats back to the database when the window is closed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveGame();
                System.exit(0);
            }
        });
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(COLOR_BG);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        topPanel.setBackground(COLOR_BG);
        JLabel selectLabel = new JLabel("Choose your pet:");
        selectLabel.setFont(FONT_MAIN);
        selectLabel.setForeground(COLOR_TEXT);
        topPanel.add(selectLabel);

        petSelector = new JComboBox<>();
        petSelector.setFont(FONT_MAIN);
        for (Pet pet : owner.getPets()) {
            petSelector.addItem(pet.getName());
        }
        petSelector.addActionListener(e -> selectPet());
        topPanel.add(petSelector);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(COLOR_PANEL);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(220, 225, 230), 1, true)
        ));

        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        soundLabel = new JLabel("Select a pet to begin", SwingConstants.CENTER);
        soundLabel.setFont(FONT_TITLE);
        soundLabel.setForeground(COLOR_TEXT);
        soundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(imageLabel);
        centerPanel.add(soundLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 10, 15));
        statsPanel.setBackground(COLOR_PANEL);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        hungerBar = makeBar(Color.decode("#FF6B6B"));
        happinessBar = makeBar(Color.decode("#4ECDC4"));
        energyBar = makeBar(Color.decode("#45B7D1"));

        statsPanel.add(labeledBar("Hunger", hungerBar));
        statsPanel.add(labeledBar("Happiness", happinessBar));
        statsPanel.add(labeledBar("Energy", energyBar));

        centerPanel.add(statsPanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(COLOR_BG);

        JButton feedBtn = styleButton(new JButton("Feed"));
        JButton playBtn = styleButton(new JButton("Play"));
        JButton sleepBtn = styleButton(new JButton("Sleep"));
        JButton soundBtn = styleButton(new JButton("Speak"));

        feedBtn.addActionListener(e -> {
            selectedPet.feed();
            refresh();
        });
        playBtn.addActionListener(e -> {
            selectedPet.play();
            refresh();
        });
        sleepBtn.addActionListener(e -> {
            selectedPet.sleep();
            refresh();
        });
        soundBtn.addActionListener(e -> makeSound());

        buttonPanel.add(feedBtn);
        buttonPanel.add(playBtn);
        buttonPanel.add(sleepBtn);
        buttonPanel.add(soundBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        selectPet();
        setLocationRelativeTo(null);
        setVisible(true);

        startHungerTimer();
        startHappinessTimer();
    }

    private JButton styleButton(JButton btn) {
        btn.setFont(FONT_MAIN);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(COLOR_TEXT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        return btn;
    }

    private void startHungerTimer() {
        Timer hungerTimer = new Timer(10_000, event -> {
            for (Pet pet : owner.getPets()) {
                pet.increaseHunger(5);
                if (pet.getHunger() >= hungry_threshold) {
                    pet.hungrySound();
                }
            }
            refresh();
        });
        hungerTimer.start();
    }

    private void startHappinessTimer() {
        Timer HappyTimer = new Timer(10_000, event -> {
            for (Pet pet : owner.getPets()) {
                if (pet.getHunger() >= hungry_threshold) {
                    pet.decreaseHappiness(10);
                } else {
                    pet.decreaseHappiness(5);
                }
                if (pet.getHappiness() <= 20) {
                    System.out.println(pet.getName() + " is very unhappy right now!");
                }
            }
            refresh();
        });
        HappyTimer.start();
    }

    private JProgressBar makeBar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setForeground(color);
        bar.setBackground(new Color(235, 235, 235));
        bar.setBorderPainted(false);
        bar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return bar;
    }

    private JPanel labeledBar(String name, JProgressBar bar) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(COLOR_PANEL);
        JLabel label = new JLabel(name);
        label.setFont(FONT_MAIN);
        label.setForeground(COLOR_TEXT);
        label.setPreferredSize(new Dimension(80, 20));
        panel.add(label, BorderLayout.WEST);
        panel.add(bar, BorderLayout.CENTER);
        return panel;
    }

    private void selectPet() {
        int index = petSelector.getSelectedIndex();
        if (index >= 0) {
            selectedPet = owner.getPets().get(index);
            updatePetImage();
            soundLabel.setText(selectedPet.getName() + " is ready!");
            refresh();
        }
    }

    private void updatePetImage() {
        String type = selectedPet.getClass().getSimpleName().toLowerCase();
        String imagePath = "images/" + type + ".png";
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setIcon(null);
            imageLabel.setText("(Image missing: " + imagePath + ")");
        }
    }

    private void refresh() {
        hungerBar.setValue(selectedPet.getHunger());
        happinessBar.setValue(selectedPet.getHappiness());
        energyBar.setValue(selectedPet.getEnergy());
    }

    private void makeSound() {
        selectedPet.makeSound();
        soundLabel.setText(selectedPet.getName() + " says hello!");
    }

// Save every pet's current stats to the database, replacing old rows so nothing duplicates
    private void saveGame() {
        DatabaseConnection.clearPets(); //clear the table first to avoid duplicate rows
        for (Pet pet : owner.getPets()) {
            DatabaseConnection.savePet(pet, owner.getName()); //save each pet's current state
        }
        System.out.println("[DB] Game saved on exit.");
    }

  public static void main(String[] args) {
        DatabaseConnection.initialize();

        Owner owner = new Owner(1, "Alex");

        java.util.ArrayList<Pet> saved = DatabaseConnection.loadAllPets();

        if (saved.isEmpty()) {
            owner.adoptPet(new Dog("Rex", "Blue Heeler"));
            owner.adoptPet(new Cat("Whiskers", true));
            owner.adoptPet(new Dragon("Smaug", 100));
        } else {
            for (Pet p : saved) {
                owner.adoptPet(p);
            }
        }

        SwingUtilities.invokeLater(() -> new PetGameGUI(owner));
    }
}