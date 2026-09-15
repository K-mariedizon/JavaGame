import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScore {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void HS() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HighScore window = new HighScore();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public HighScore() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 971, 575);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(HighScore.class.getResource("/bgImages/hgCont.png")));
        lblNewLabel.setBounds(0, 0, 1014, 540);
        frame.getContentPane().add(lblNewLabel);

        JTextArea highScoreArea = new JTextArea();
        highScoreArea.setEditable(false);
        highScoreArea.setFont(new Font("Arial", Font.PLAIN, 18));
        highScoreArea.setForeground(Color.decode("#ede2d4")); 
        highScoreArea.setOpaque(false);
        highScoreArea.setBounds(500, 185, 300, 300);
        lblNewLabel.add(highScoreArea);

        loadHighScores(highScoreArea);

        JButton btnBack = new JButton("");
        btnBack.setIcon(new ImageIcon(HighScore.class.getResource("/btnImages/BACK.jpg")));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu c = new Menu();
                c.main(null);
                frame.dispose();
            }
        });
        btnBack.setBackground(new Color(19, 26, 44));
        btnBack.setBounds(61, 475, 169, 40);
        frame.getContentPane().add(btnBack);
    }

    /**
     * Load high scores from the text file, sort them, and display in the JTextArea.
     */
    private void loadHighScores(JTextArea highScoreArea) {
        String filePath = "src/highscore.txt"; 
        ArrayList<ScoreEntry> scoreList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Each line is in the format "PlayerName, Time"
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String playerName = parts[0];
                    long time = Long.parseLong(parts[1].replace(" seconds", "")); // Remove " seconds" and parse time
                    scoreList.add(new ScoreEntry(playerName, time));
                }
            }

            // Sort the score list by time (ascending order)
            Collections.sort(scoreList, new Comparator<ScoreEntry>() {
                @Override
                public int compare(ScoreEntry s1, ScoreEntry s2) {
                    return Long.compare(s1.getTime(), s2.getTime());
                }
            });

            // Build the string to display in the JTextArea
            StringBuilder scores = new StringBuilder();
            for (ScoreEntry entry : scoreList) {
                scores.append(entry.getPlayerName()).append("\t\t").append(entry.getTime()).append(" secs\n");
            }

            highScoreArea.setText(scores.toString());

        } catch (IOException e) {
            highScoreArea.setText("Error loading high scores: " + e.getMessage());
        }
    }

    /**
     * Helper class to represent a player's score.
     */
    private static class ScoreEntry {
        private String playerName;
        private long time;

        public ScoreEntry(String playerName, long time) {
            this.playerName = playerName;
            this.time = time;
        }

        public String getPlayerName() {
            return playerName;
        }

        public long getTime() {
            return time;
        }
    }
}
