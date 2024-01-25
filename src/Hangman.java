import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman extends JFrame implements ActionListener {
    private JButton startButton;
    private JLabel currentWordLabel;
    private JLabel congLabel;
    private ArrayList<String> wordsList;
    private ArrayList<Character> missedLetters;
    
    Hangman() {
        createLayout();
    }
    public void setMainFrame() {
        setTitle("Hangman Game"); // set title of frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        setResizable(false); // prevent frame of being resized
        setSize(Utils.FRAME_WIDTH, Utils.FRAME_HEIGHT); // set the x and y dimension of frame
        setLayout(null);
        setLocationRelativeTo(null); //centre window
    }

    public void setWelcomeLabel() {
        JPanel titlePanel = new JPanel();
        JLabel welcomeText = new JLabel(); // create label for welcome text
        welcomeText.setText("Welcome to Hangman Game"); // set label text
        welcomeText.setFont(new Font(null,Font.BOLD,18));
        titlePanel.add(welcomeText);
        titlePanel.setBounds(50, 20, 500, 30);
        add(titlePanel);
    }

    private void createLayout() {
        setMainFrame();
        setWelcomeLabel();
        setStartGameButton();
        setResultLayout();
        setVisible(true); // make frame visible
    }

    private void setStartGameButton() {
        startButton = new JButton("Start the Game");
        startButton.setBounds(200, 60, 190, 40);
        startButton.addActionListener(this);
        add(startButton);
    }

    private void setResultLayout() {
        currentWordLabel = new JLabel();
        congLabel = new JLabel();
        currentWordLabel.setBounds(220, 90, 500, 40);
        congLabel.setBounds(130, 150, 500, 40);
        congLabel.setFont(new Font(null,Font.BOLD,15));
        add(currentWordLabel);
        add(congLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            playHangmanGame();
        }
    }

    private void playHangmanGame() {
        startButton.setVisible(false);
        // Read words from file and store in ArrayList
        wordsList = new ArrayList<>();
        // Keep track of missed letters
        missedLetters = new ArrayList<>();

        try {
            Scanner file = new Scanner(new File(Utils.FILE_PATH));
            while (file.hasNextLine()) {
                wordsList.add(file.nextLine().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Random rand = new Random();
        int wordIndex = rand.nextInt(wordsList.size());
        String word = wordsList.get(wordIndex);
        char[] wordArray = word.toCharArray();
        char[] asterisks = new char[wordArray.length];
        for (int j = 0; j < asterisks.length; j++) {
            asterisks[j] = '*';
        }
        currentWordLabel.setText("Current word: " + new String(asterisks));

        while (true) {
            String userInput = JOptionPane.showInputDialog("Enter a letter:");
            System.out.println(userInput);
            if (userInput == null || userInput.length() > 1) {
                JOptionPane.showMessageDialog(null, "You can only add one char at a time", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                char guess = userInput.charAt(0);
                if (word.contains(String.valueOf(guess))) {
                    for (int i = 0; i < wordArray.length; i++) {
                        if (wordArray[i] == guess) {
                            asterisks[i] = wordArray[i];
                        }
                    }
                    System.out.println(asterisks);
                    currentWordLabel.setText("Current word: " + new String(asterisks));
                    // Check if all letters have been revealed
                    boolean allRevealed = true;
                    for (char c : asterisks) {
                        if (c == '*') {
                            allRevealed = false;
                            break;
                        }
                    }
                    if (allRevealed) {
                        congLabel.setText("Congratulations! The word is '" + word + "'." + " " + "You missed " + missedLetters.size() + " times.");
                        wordsList.clear();
                        missedLetters.clear();
                        askUserToAddWord();
                        break;
                    }
                } else {
                    boolean isContainInList = false;
                    for (Character missedLetter : missedLetters) {
                        if (missedLetter.equals(guess)) {
                            isContainInList = true;
                            JOptionPane.showMessageDialog(null, "You've already tried '" + guess + "', try another letter.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if (!isContainInList) {
                        missedLetters.add(guess);
                    }
                }
            }
        }
    }

    private void askUserToAddWord() {
        ArrayList<String> wordsList = new ArrayList();
        String newWord = JOptionPane.showInputDialog("Enter new word to save into file. ");

        if(newWord!=null) {
            File file = new File(Utils.FILE_PATH);
            try (Scanner input = new Scanner(file)) {
                while (input.hasNextLine()) {
                    wordsList.add(input.nextLine().toLowerCase());
                }
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage());
            }

            try {
                wordsList.add(newWord);
                PrintWriter writer = new PrintWriter(file);
                for (String w : wordsList) {
                    writer.println(w);
                }
                writer.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        askUserToContinueGame();
    }

    private void askUserToContinueGame() {
        int a = JOptionPane.showConfirmDialog(this, "Do you want to continue to new game?");
        if (a == JOptionPane.YES_OPTION) {
            congLabel.setVisible(false);
            playHangmanGame();
        } else {
            dispose();
            new Hangman();
        }
    }
}
