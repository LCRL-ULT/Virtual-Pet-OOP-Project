package ui;

import database.DatabaseConnection;
import java.awt.*;
import java.util.ArrayList;
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

    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PANEL = Color.WHITE;
    private static final Color COLOR_TEXT = new Color(44, 62, 80);
    private static final Font FONT_MAIN = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    public PetGameGUI(Owner owner) {
        this.owner = owner;
        DatabaseConnection.initialize();

<<<<<<< HEAD
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
=======
        setTitle("Virtual Pet Game - Playing as: " + owner.getName());
        setSize(480, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
>>>>>>> origin/main
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

        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setBackground(COLOR_BG);
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        JPanel buttonPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        buttonPanel.setBackground(COLOR_BG);

        JButton feedBtn = styleButton(new JButton("Feed"));
        JButton playBtn = styleButton(new JButton("Play"));
        JButton sleepBtn = styleButton(new JButton("Sleep"));
        JButton soundBtn = styleButton(new JButton("Speak"));
<<<<<<< HEAD

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
=======
        JButton specialBtn = styleButton(new JButton("Special"));
        JButton storeBtn = styleButton(new JButton("Store"));
        JButton adoptBtn = styleButton(new JButton("Adopt"));
        
        feedBtn.addActionListener(e -> showFeedDialog());
        playBtn.addActionListener(e -> { selectedPet.play(); refresh(); passTime(); });
        sleepBtn.addActionListener(e -> { selectedPet.sleep(); refresh(); passTime(); });
        soundBtn.addActionListener(e -> { makeSound(); passTime(); });
        specialBtn.addActionListener(e -> { useSpecialAbility(); passTime(); });
        storeBtn.addActionListener(e -> showStoreDialog());
        adoptBtn.addActionListener(e -> showAdoptDialog());
>>>>>>> origin/main

        buttonPanel.add(feedBtn);
        buttonPanel.add(playBtn);
        buttonPanel.add(sleepBtn);
        buttonPanel.add(soundBtn);
        buttonPanel.add(specialBtn);
        buttonPanel.add(storeBtn);
        buttonPanel.add(adoptBtn);
        
        buttonContainer.add(buttonPanel, BorderLayout.CENTER);
        add(buttonContainer, BorderLayout.SOUTH);

        selectPet();
        setLocationRelativeTo(null); 
        setVisible(true);

        startTimers(); 
    }

    private void showAdoptDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Pet Type:"));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Dog", "Cat", "Dragon"});
        panel.add(typeBox);
        panel.add(new JLabel("Pet Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adopt a New Pet", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String pName = nameField.getText().trim();
            if (pName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please give your pet a name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String pType = (String) typeBox.getSelectedItem();
            Pet newPet = null;
            if (pType.equals("Dog")) newPet = new Dog(pName, "Mixed Breed");
            else if (pType.equals("Cat")) newPet = new Cat(pName, true);
            else if (pType.equals("Dragon")) newPet = new Dragon(pName, 100);

            owner.adoptPet(newPet);
            
            petSelector.addItem(newPet.getName());
            petSelector.setSelectedItem(newPet.getName());
            
            JOptionPane.showMessageDialog(this, pName + " has joined your family!");
        }
    }

    private void showFeedDialog() {
        if (owner.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your inventory is empty! Visit the Store first.", "Empty Inventory", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] foodNames = new String[owner.getInventory().size()];
        for (int i = 0; i < owner.getInventory().size(); i++) {
            FoodItem f = owner.getInventory().get(i);
            foodNames[i] = f.getName() + " (+" + f.getNutrition() + " fullness)";
        }

        String selected = (String) JOptionPane.showInputDialog(
            this, "What will you feed " + selectedPet.getName() + "?",
            "Feed Pet", JOptionPane.QUESTION_MESSAGE, null,
            foodNames, foodNames[0]);

        if (selected != null) {
            for (int i = 0; i < foodNames.length; i++) {
                if (foodNames[i].equals(selected)) {
                    FoodItem food = owner.getInventory().get(i);
                    selectedPet.feed(food);
                    owner.getInventory().remove(i);
                    soundLabel.setText(selectedPet.getName() + " ate a " + food.getName() + "!");
                    refresh();
                    passTime();
                    break;
                }
            }
        }
    }

    private void showStoreDialog() {
        String[] storeItems = {
            "Apple (15 Nutrition)", "Fish (20 Nutrition)",
            "Steak (30 Nutrition)", "Dragon Fruit (50 Nutrition)"
        };

        String selected = (String) JOptionPane.showInputDialog(
            this, "Welcome to the Store!\nSelect an item to grab:",
            "Food Store", JOptionPane.PLAIN_MESSAGE, null,
            storeItems, storeItems[0]);

        if (selected != null) {
            FoodItem newFood = null;
            if (selected.startsWith("Apple")) newFood = new FoodItem("Apple", 15);
            else if (selected.startsWith("Fish")) newFood = new FoodItem("Fish", 20);
            else if (selected.startsWith("Steak")) newFood = new FoodItem("Steak", 30);
            else if (selected.startsWith("Dragon")) newFood = new FoodItem("Dragon Fruit", 50);

            if (newFood != null) {
                owner.getInventory().add(newFood);
                JOptionPane.showMessageDialog(this, "Added " + newFood.getName() + " to your inventory!");
            }
        }
    }

    private void useSpecialAbility() {
        if (selectedPet instanceof Dragon) ((Dragon) selectedPet).breatheFire();
        else if (selectedPet instanceof Dog) ((Dog) selectedPet).fetch();
        else if (selectedPet instanceof Cat) ((Cat) selectedPet).scratch();
        
        soundLabel.setText(selectedPet.getName() + " used a special ability!");
        refresh();
    }

    private void passTime() {
        for (Pet pet : owner.getPets()) {
            pet.increaseHunger(2); 
            if (pet.getHunger() >= hungry_threshold) pet.decreaseHappiness(5);
        }
        refresh();
    }

    private void startTimers(){
        Timer gameTimer = new Timer(10_000, event -> {
            for (Pet pet : owner.getPets()) {
                pet.increaseHunger(5);
                if (pet.getHunger() >= hungry_threshold) {
                    pet.decreaseHappiness(10);
                    pet.hungrySound();
                } else {
                    pet.decreaseHappiness(5);
                }
            }
            refresh();
        });
        gameTimer.start();
    }

    private static JButton styleButton(JButton btn) {
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

<<<<<<< HEAD
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

=======
>>>>>>> origin/main
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
        if (selectedPet != null) {
            hungerBar.setValue(selectedPet.getHunger());
            happinessBar.setValue(selectedPet.getHappiness());
            energyBar.setValue(selectedPet.getEnergy());
        }
    }

    private void makeSound() {
        selectedPet.makeSound();
        soundLabel.setText(selectedPet.getName() + " says hello!");
    }

<<<<<<< HEAD
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
=======
    public static Owner runSetupScreen() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}

        JDialog setupDialog = new JDialog((Frame)null, "Welcome to the Adoption Center", true);
        setupDialog.setSize(400, 450);
        setupDialog.setLocationRelativeTo(null);
        setupDialog.setLayout(new BorderLayout(10, 10));
        setupDialog.getContentPane().setBackground(COLOR_BG);

        JPanel contentPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        contentPanel.setBackground(COLOR_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(new JLabel("What is your name?"));
        JTextField ownerNameField = new JTextField();
        contentPanel.add(ownerNameField);

        contentPanel.add(new JLabel("What kind of pet do you want to adopt?"));
        JComboBox<String> petTypeBox = new JComboBox<>(new String[]{"Dog", "Cat", "Dragon"});
        contentPanel.add(petTypeBox);

        contentPanel.add(new JLabel("What will you name this pet?"));
        JTextField petNameField = new JTextField();
        contentPanel.add(petNameField);

        setupDialog.add(contentPanel, BorderLayout.NORTH);

        DefaultListModel<String> adoptedModel = new DefaultListModel<>();
        JList<String> adoptedList = new JList<>(adoptedModel);
        JScrollPane scrollPane = new JScrollPane(adoptedList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your New Pets"));
        setupDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(COLOR_BG);
        
        JButton adoptBtn = styleButton(new JButton("Adopt Pet"));
        JButton startBtn = styleButton(new JButton("Start Game"));
        
        ArrayList<Pet> tempPets = new ArrayList<>();

        adoptBtn.addActionListener(e -> {
            String pName = petNameField.getText().trim();
            if (pName.isEmpty()) {
                JOptionPane.showMessageDialog(setupDialog, "Please give your pet a name!");
                return;
            }
            String pType = (String) petTypeBox.getSelectedItem();
            Pet newPet = null;
            if (pType.equals("Dog")) newPet = new Dog(pName, "Blue Heeler");
            else if (pType.equals("Cat")) newPet = new Cat(pName, true);
            else if (pType.equals("Dragon")) newPet = new Dragon(pName, 100);

            tempPets.add(newPet);
            adoptedModel.addElement(pName + " (" + pType + ")");
            petNameField.setText(""); 
        });

        Owner[] finalOwner = new Owner[1]; 

        startBtn.addActionListener(e -> {
            String oName = ownerNameField.getText().trim();
            if (oName.isEmpty()) {
                JOptionPane.showMessageDialog(setupDialog, "Please enter your name!");
                return;
            }
            if (tempPets.isEmpty()) {
                JOptionPane.showMessageDialog(setupDialog, "You must adopt at least one pet!");
                return;
            }
            
            Owner newOwner = new Owner(1, oName);
            for (Pet p : tempPets) {
                newOwner.adoptPet(p);
            }
            newOwner.getInventory().add(new FoodItem("Apple", 15));
            newOwner.getInventory().add(new FoodItem("Steak", 30));
            
            finalOwner[0] = newOwner;
            setupDialog.dispose(); 
        });

        buttonPanel.add(adoptBtn);
        buttonPanel.add(startBtn);
        setupDialog.add(buttonPanel, BorderLayout.SOUTH);

        setupDialog.setVisible(true); 
        if (finalOwner[0] == null) System.exit(0);
        
        return finalOwner[0];
    }

   public static void main(String[] args) {
        Owner player = runSetupScreen();
        DatabaseConnection.clearPets(); 
        player.saveToDatabase();
        SwingUtilities.invokeLater(() -> new PetGameGUI(player));
>>>>>>> origin/main
    }
}