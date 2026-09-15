import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MysteryGame extends JFrame {
    private ArrayList<HiddenObject> hiddenObjects ; 
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private List<String> clues;
    private String riddle;
    private String riddleAnswer;
    private int foundCount = 0;
    private ArrayList<String> collectedClues = new ArrayList<>();
    private JLabel cluesLabel;
    private JLabel itemLabel;
    private Image backgroundImage;
    private Random random = new Random();
    private String playerName;
    private Instant startTime;
    private int answeredMathQuestions = 0; 

    public MysteryGame(String playerName) {
        setTitle("Mystery Hidden Object Game");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.playerName = playerName;
        this.startTime = Instant.now(); 
        
        
        loadQuestionsAndAnswers();
        loadCluesAndRiddle();

        String[] bgImages = {
                "src/roomImages/room1.png",
                "src/roomImages/room2.png",
                "src/roomImages/room3.png"
        };
        String selectedBgImage = bgImages[random.nextInt(bgImages.length)];
        backgroundImage = new ImageIcon(selectedBgImage).getImage();

        // Item sets
        String[][] itemSets = {
                {
                        "src/itemsImages/bug.png",
                        "src/itemsImages/bug.png",
                        "src/itemsImages/bug.png",
                        "src/itemsImages/bug.png",
                        "src/itemsImages/bug.png"
                },
                {
                    "src/itemsImages/key.png",
                    "src/itemsImages/key.png",
                    "src/itemsImages/key.png",
                    "src/itemsImages/key.png",
                    "src/itemsImages/key.png"
                },
                {
                        "src/itemsImages/hair.png",
                        "src/itemsImages/hair.png",
                        "src/itemsImages/hair.png",
                        "src/itemsImages/hair.png",
                        "src/itemsImages/hair.png"
                }
        };

        //random set of items
        String[] selectedItemSet = itemSets[random.nextInt(itemSets.length)];

        hiddenObjects = new ArrayList<>();
        for (String itemPath : selectedItemSet) {
            int x = random.nextInt(850); // Prevent going outside the frame
            int y = random.nextInt(385);
            hiddenObjects.add(new HiddenObject(x, y, itemPath));
        }

        setLayout(null);

     // Clues
     cluesLabel = new JLabel("");
     cluesLabel.setFont(new Font("Segoe Print", Font.PLAIN, 18));
     cluesLabel.setHorizontalAlignment(SwingConstants.LEFT);
     cluesLabel.setForeground(Color.decode("#ede2d4"));
     cluesLabel.setOpaque(false); 
     cluesLabel.setBounds(250, 485, 400, 40); 
     add(cluesLabel);
     
     //itemlabel
     itemLabel = new JLabel("" + getItemName(selectedItemSet));
     itemLabel.setFont(new Font("Segoe Print", Font.PLAIN, 16));
     itemLabel.setHorizontalAlignment(SwingConstants.LEFT);
     itemLabel.setForeground(Color.decode("#ede2d4"));
     itemLabel.setOpaque(false);
     itemLabel.setBounds(73, 6, 300, 40);
     add(itemLabel);

     GamePanel gamePanel = new GamePanel();
     gamePanel.setBounds(0, 0, getWidth(), getHeight());
     add(gamePanel);

     setVisible(true);
     
     gamePanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
             int x = e.getX();
             int y = e.getY();

             for (HiddenObject obj : hiddenObjects) {
                 if (!obj.isFound() && obj.getBounds().contains(x, y)) {
                     obj.setFound(true);
                     foundCount++;
                     gamePanel.repaint();
                     askMathQuestion(); 
                     break;
                 }
             }
         }
     });
     //itemLabel.repaint();
 }

 // Getting name of item based on the item path
 private String getItemName(String[] selectedItemSet) {
     if (selectedItemSet[0].contains("key")) {
         return "Key";
     } else if (selectedItemSet[0].contains("hair")) {
         return "Hair";
     } else if (selectedItemSet[0].contains("bug")) {
         return "Bug";
     }
     return "Unknown";
 }

 // Update item label with the next item name
 private void updateItemLabel(String[] selectedItemSet) {
     String itemName = getItemName(selectedItemSet);
     itemLabel.setText(itemName);
 }

    private void loadQuestionsAndAnswers() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        try (BufferedReader questionReader = new BufferedReader(new FileReader("src/mathquestions.txt"));
             BufferedReader answerReader = new BufferedReader(new FileReader("src/mathanswers.txt"))) {

            String question;
            while ((question = questionReader.readLine()) != null) {
                questions.add(question);
            }

            String answer;
            while ((answer = answerReader.readLine()) != null) {
                answers.add(answer);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading questions or answers: " + e.getMessage());
        }
    }

    private void loadCluesAndRiddle() {
        clues = new ArrayList<>();
        riddle = ""; // Current riddle
        riddleAnswer = ""; // Current answer
        List<List<String>> cluesForAllRiddles = new ArrayList<>(); // Store clues for all riddles

        try (BufferedReader cluesReader = new BufferedReader(new FileReader("src/clues.txt"));
             BufferedReader riddleReader = new BufferedReader(new FileReader("src/Riddles.txt"));
             BufferedReader answerReader = new BufferedReader(new FileReader("src/answers.txt"))) {

            // Process clues
            String cluesLine;
            while ((cluesLine = cluesReader.readLine()) != null) {
                String[] cluesArray = cluesLine.split(","); // Split by commas
                List<String> riddleClues = new ArrayList<>();
                for (String clue : cluesArray) {
                    riddleClues.add(clue.trim()); // Trim whitespace and add to riddle-specific clues
                }
                cluesForAllRiddles.add(riddleClues); // Add all clues for this riddle
            }
            // Read a random riddle and its corresponding answer
            List<String> riddles = new ArrayList<>();
            List<String> answers = new ArrayList<>();
            String riddleLine;
            while ((riddleLine = riddleReader.readLine()) != null) {
                riddles.add(riddleLine.trim());
            }
            String answerLine;
            while ((answerLine = answerReader.readLine()) != null) {
                answers.add(answerLine.trim());
            }

            // Pick a random riddle
            if (!riddles.isEmpty() && !answers.isEmpty() && !cluesForAllRiddles.isEmpty()) {
                int randomIndex = (int) (Math.random() * riddles.size());
                riddle = riddles.get(randomIndex);
                riddleAnswer = answers.get(randomIndex);
                clues = cluesForAllRiddles.get(randomIndex); // Get clues for this riddle
            } else {
                JOptionPane.showMessageDialog(this, "Riddles, answers, or clues are missing!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading clues, riddle, or answer: " + e.getMessage());
        }
    }



    // RiddleData helper class
//    private static class RiddleData {
//        String riddle;
//        String answer;
//        String clues;
//
//        RiddleData(String riddle, String answer, String clues) {
//            this.riddle = riddle;
//            this.answer = answer;
//            this.clues = clues;
//        }
//    }

    
    private void askMathQuestion() {
        //random question and answer
        int questionIndex = random.nextInt(questions.size());
        String question = questions.get(questionIndex);
        String correctAnswer = answers.get(questionIndex);

        ImageIcon backgroundIcon = new ImageIcon("src/bgImages/1.png");
        Image backgroundImage = backgroundIcon.getImage();

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JFrame questionFrame = new JFrame("Math Question");
        questionFrame.setSize(400, 200);
        questionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        questionFrame.setLocationRelativeTo(this);

        JLabel questionLabel = new JLabel("<html><div style='text-align: center;'>" + question + "</div></html>", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(Color.WHITE); 
        backgroundPanel.add(questionLabel, BorderLayout.CENTER);

        // Timerlabel
        JLabel timerLabel = new JLabel("Time: 10 seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timerLabel.setForeground(Color.WHITE);
        backgroundPanel.add(timerLabel, BorderLayout.NORTH);

        //answer field
        JTextField answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(150, 30));

        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        answerPanel.setOpaque(false); 
        answerPanel.add(answerField);
        backgroundPanel.add(answerPanel, BorderLayout.SOUTH);

        questionFrame.add(backgroundPanel);

        //Timer countdown logic
        Timer timer = new Timer();
        final int[] timeRemaining = {10};

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining[0] > 0) {
                    timeRemaining[0]--;
                    timerLabel.setText("Time: " + timeRemaining[0] + " seconds");
                } else {
                    timer.cancel();
                    JOptionPane.showMessageDialog(questionFrame, "Time's up! The correct answer was: " + correctAnswer);
                    questionFrame.dispose();
                    updateCluesLabel();       
                    if (foundCount == hiddenObjects.size()) {
                        showRiddle();
                    }
                }
                
            }
        }, 1000, 1000);

        questionFrame.setVisible(true);

        // Add action listener to the answer field
        answerField.addActionListener(e -> {
            String userAnswer = answerField.getText();
            timer.cancel();
            questionFrame.dispose();

            if (userAnswer != null && userAnswer.trim().equals(correctAnswer.trim())) {
                JOptionPane.showMessageDialog(this, "Correct!");
                answeredMathQuestions++;
                collectedClues.add(clues.get(foundCount - 1)); // Add clue based on found object count
            } else {
                JOptionPane.showMessageDialog(this, "Wrong! The correct answer was: " + correctAnswer);
            }

            // Update the clues label after the question
            updateCluesLabel();
            
            if (foundCount == hiddenObjects.size()) {
                showRiddle();
            }
            itemLabel.repaint();
            
        });
    }

    
    private void updateCluesLabel() {
        StringBuilder cluesText = new StringBuilder("<html><div style='text-align: center;'>");

        for (int i = 0; i < collectedClues.size(); i++) {
            cluesText.append(collectedClues.get(i));
            if (i < collectedClues.size() - 1) {
                cluesText.append(" - ");
            }
        }
        cluesText.append("</div></html>");

        cluesLabel.setText(cluesText.toString());
        cluesLabel.repaint();
    }

    
    private void saveHighScore(String playerName, long timeElapsed) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/highscore.txt", true))) {
            writer.write(playerName + ", " + timeElapsed + " seconds");
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving high score: " + e.getMessage());
        }
    }
    
    private void endGame() {
        // Calculate time elapsed
        Instant endTime = Instant.now();
        long timeElapsed = java.time.Duration.between(startTime, endTime).toSeconds();

        //game summary
        JOptionPane.showMessageDialog(this, "Congratulations, " + playerName + "! You finished the game in " + timeElapsed + " seconds.");

        //Save
        saveHighScore(playerName, timeElapsed);

     // Open the HighScore window
        HighScore hs = new HighScore();
		hs.HS();
        this.dispose();
    }	
    
    
    private void showRiddle() {
        // Load the clue and riddle information
        if (riddle == null || riddleAnswer == null) {
            JOptionPane.showMessageDialog(this, "Riddle or Answer not loaded properly.");
            return;
        }

        //background image for the riddle 
        ImageIcon backgroundIcon = new ImageIcon("src/bgImages/2.png"); 
        Image backgroundImage = backgroundIcon.getImage();

        //JPanel for the background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        //Frame riddle
        JFrame riddleFrame = new JFrame("Riddle");
        riddleFrame.setSize(500, 280);
        riddleFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        riddleFrame.setLocationRelativeTo(this);

        //background panel
        JLabel riddleLabel = new JLabel("<html><div style='text-align: center;'>" + riddle + "</div></html>", SwingConstants.CENTER);
        riddleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        riddleLabel.setForeground(Color.decode("#ede2d4")); 
        backgroundPanel.add(riddleLabel, BorderLayout.CENTER);

        //answer field
        JTextField answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(200, 43));

        //answer field panel
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        answerPanel.setOpaque(false); // Transparent panel
        answerPanel.add(answerField);
        backgroundPanel.add(answerPanel, BorderLayout.SOUTH);

        riddleFrame.add(backgroundPanel);

        // Display the riddle frame
        riddleFrame.setVisible(true);

        // Add action listener to the answer field
        answerField.addActionListener(e -> {
            String userAnswer = answerField.getText().trim();

            if (userAnswer.equalsIgnoreCase(riddleAnswer.trim())) {
                JOptionPane.showMessageDialog(this, "Correct! You solved the riddle!");
            } else {
                JOptionPane.showMessageDialog(this, "Wrong! The correct answer was: " + riddleAnswer);
            }

            riddleFrame.dispose();
            endGame();
        });
    }


    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the background image, scaled to fit the panel size
            g.drawImage(backgroundImage, 0, 0, 900, 565, this);

            for (HiddenObject obj : hiddenObjects) {
                obj.draw(g);
            }
        }
    }

    private class HiddenObject {
        private int x, y;
        private boolean found;
        private Image image;

        public HiddenObject(int x, int y, String imagePath) {
            this.x = x;
            this.y = y;
            this.found = false;

            // Load the image
            ImageIcon icon = new ImageIcon(imagePath);
            this.image = icon.getImage();
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, 60, 60);
        }

        public boolean isFound() {
            return found;
        }

        public void setFound(boolean found) {
            this.found = found;
        }

        public void draw(Graphics g) {
            if (!found) {
                g.drawImage(image, x, y, 70, 70, null);
            } else {
                g.setColor(Color.GREEN);
                g.drawString("Found!", x + 10, y + 30);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String playerName = JOptionPane.showInputDialog("Enter your name:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                new MysteryGame(playerName);
            }
        });
    }
} 
