import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class CasinoGame {
    private static final String SCORES_FILE = "scores.txt";
    private static final Random random = new Random();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CasinoGame::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Casino Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(34, 139, 34));  // Forest green background
        frame.add(panel);
        
        JLabel titleLabel = new JLabel("Slot Machine");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(130, 20, 300, 50);
        panel.add(titleLabel);
        
        JLabel slotLabel = new JLabel("Slots:");
        slotLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        slotLabel.setForeground(Color.WHITE);
        slotLabel.setBounds(50, 100, 100, 25);
        panel.add(slotLabel);
        
        JLabel slotResult = new JLabel("0 0 0");
        slotResult.setFont(new Font("Serif", Font.BOLD, 36));
        slotResult.setForeground(Color.YELLOW);
        slotResult.setBounds(150, 90, 200, 50);
        panel.add(slotResult);
        
        JButton spinButton = new JButton("Spin");
        spinButton.setFont(new Font("Arial", Font.BOLD, 24));
        spinButton.setBackground(Color.RED);
        spinButton.setForeground(Color.WHITE);
        spinButton.setBounds(200, 200, 100, 50);
        panel.add(spinButton);
        
        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultLabel.setForeground(Color.YELLOW);
        resultLabel.setBounds(50, 300, 400, 25);
        panel.add(resultLabel);
        
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int slot1 = random.nextInt(10);
                int slot2 = random.nextInt(10);
                int slot3 = random.nextInt(10);
                slotResult.setText(slot1 + " " + slot2 + " " + slot3);

                if (slot1 == slot2 && slot2 == slot3) {
                    resultLabel.setText("You win!");
                    resultLabel.setForeground(Color.GREEN);
                    updateScores(10); // For example, add 10 points for a win
                } else {
                    resultLabel.setText("Try again!");
                    resultLabel.setForeground(Color.RED);
                }
                displayTopScore(panel);
            }
        });
        
        frame.setVisible(true);
    }

    private static void updateScores(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE, true))) {
            writer.write(String.valueOf(score));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayTopScore(JPanel panel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            int topScore = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                int score = Integer.parseInt(line);
                if (score > topScore) {
                    topScore = score;
                }
            }
            JLabel topScoreLabel = new JLabel("Top Score: " + topScore);
            topScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            topScoreLabel.setForeground(Color.YELLOW);
            topScoreLabel.setBounds(50, 330, 400, 25);
            panel.add(topScoreLabel);
            panel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}