import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class Start {
    private JFrame frame;
    private Clip audioClip; // Audio clip for music
    public static String playerName;

    public static String getPlayerName() {
        return playerName;
    }

    public static void start() {
        EventQueue.invokeLater(() -> {
            try {
                Start window = new Start();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Start() {
        initialize();
        playMusic1("src/bgImages/Music.wav"); 
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 974, 578);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(Start.class.getResource("/bgImages/startbg.png")));
        lblNewLabel.setBounds(0, 0, 964, 540);
        frame.getContentPane().add(lblNewLabel);

        JTextField nameField = new JTextField();
        nameField.setDropMode(DropMode.INSERT);
        nameField.setBounds(387, 190, 200, 30);
        lblNewLabel.add(nameField);

        JButton startGameButton = new JButton("");
        startGameButton.setIcon(new ImageIcon(Start.class.getResource("/btnImages/startgame.jpg")));
        startGameButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setBackground(new Color(19, 26, 44));
        startGameButton.setBounds(380, 340, 200, 40);
        lblNewLabel.add(startGameButton); // Add button on top of background

        startGameButton.addActionListener(e -> {
            playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your name before starting the game.");
            } else {
                frame.dispose();
                SwingUtilities.invokeLater(() -> {
                    MysteryGame game = new MysteryGame(playerName); 
                    game.setVisible(true); // Launch the game
                });
            }
        });

        // Remove the mute button since it will no longer be used
        // muteButton.remove();   // Commented out if you want to hide the mute button entirely
        // Or, leave it in place but disable it:
        // muteButton.setEnabled(false);  // Disabling mute button if you don't want it to have any effect.

        JButton btnBack = new JButton("");
        btnBack.setIcon(new ImageIcon(Start.class.getResource("/btnImages/BACK.jpg")));
        btnBack.setBackground(new Color(19, 26, 44));
        btnBack.setBounds(56, 474, 175, 43);
        frame.getContentPane().add(btnBack);

        btnBack.addActionListener(e -> {
            Menu c = new Menu();
            c.main(null);
            frame.dispose();
        });
    }

    private void playMusic1(String audioFilePath) {
        try {
            if (audioClip != null) {
                audioClip.stop();  // Stop any currently playing clip
                audioClip.close(); // Close it to release resources
            }
            File audioFile = new File(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the audio indefinitely
            audioClip.start(); // Start playing immediately
            System.out.println("Music started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePlayerName(String playerName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/highscore.txt", true))) {
            writer.write(playerName);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving player name: " + e.getMessage());
        }
    }
}