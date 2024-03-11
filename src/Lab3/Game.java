package Lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game implements KeyListener {
    private boolean gameOver = false;
    private boolean nameEntered = false;
    private ArrayList<Wall> walls;
    private ArrayList<Ball> balls;
    private SquareCollection boxes;
    private Bat bat;
    private Score score;
    private ScoreManager scoreManager;
    private Roof roof;
    private GameBoard board;
    private String scoreFilename = "Scores.file"; // Change this to your desired filename
    private JFrame frame;
    private JTextField nameField;
    private JButton submitButton;

    public Game(GameBoard board) {
        this.board = board;
        score = new Score(this);
        scoreManager = new ScoreManager(); // Initialize scoreManager
        balls = new ArrayList<Ball>();
        bat = new Bat(400, 550, 100, 10, balls);
        boxes = new SquareCollection(balls, score, scoreManager);
        walls = new ArrayList<Wall>();

        // Add walls
        walls.add(new Wall(0, 0, 1, board.getPreferredSize().height, walls, balls));
        walls.add(new Wall(board.getPreferredSize().width - 1, 0, 1, board.getPreferredSize().height, walls, balls));

        // Add roof
        roof = new Roof(0, 0, board.getPreferredSize().width, 1, balls);

        // Calculate the initial position based on the game board dimensions
        int initialX = board.getPreferredSize().width / 2 - Const.BallSize / 2;
        int initialY = board.getPreferredSize().height / 2 - Const.BallSize / 2;
        balls.add(new Ball(initialX, initialY, Const.BallSize, Const.BallSize));

        // Add the Game instance as a KeyListener to receive key events
        board.addKeyListener(this);
        board.setFocusable(true);
        board.requestFocus();

        // Create the frame for inputting the name
        frame = new JFrame("Enter Your Name");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        nameField = new JTextField(20);
        frame.add(nameField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(submitActionListener);
        frame.add(submitButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(cancelActionListener);
        frame.add(cancelButton);
        
        frame.setVisible(false); // Initially set JFrame invisible
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private void endGame() {
        gameOver = true;
        // Add the final score to the score manager
        scoreManager.addScore(score.getScore(), scoreFilename);
        frame.setVisible(true); // Make the JFrame visible when the game is over
    }

    public void update(Keyboard keyboard) {
        if (!gameOver) {
            // Update all game components only if the game is not over
            for (Ball sp : balls) {
                sp.update(keyboard);
            }
            for (Wall sp : walls) {
                sp.update(keyboard);
            }
            roof.update(keyboard); // Update the roof
            boxes.update(keyboard);
            bat.update(keyboard);

            // Check if all balls are out of the board
            boolean allBallsOutOfBoard = true;
            for (Ball ball : balls) {
                if (!ball.isOutOfBoard()) {
                    allBallsOutOfBoard = false;
                    break;
                }
            }

            // If all balls are out of the board, end the game
            if (allBallsOutOfBoard) {
                endGame(); // Call endGame method to trigger game over state
            }
        }
    }

    public void draw(Graphics2D graphics) {
        for (Wall sp : walls) {
            sp.draw(graphics);
        }
        for (Ball sp : balls) {
            sp.draw(graphics);
        }
        boxes.draw(graphics);
        bat.draw(graphics);
        roof.draw(graphics);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics.drawString("Score: " + score.getScore(), 700, 580);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics.drawString("lives left: " + Const.lives, 10, 580);

        if (gameOver) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, 800, 600);
            // Draw "Game Over" message
            graphics.setColor(Color.ORANGE);
            graphics.setFont(new Font("Arial", Font.BOLD, 30));
            String gameOverMessage = "Game Over!";
            int messageWidth = graphics.getFontMetrics().stringWidth(gameOverMessage);
            graphics.drawString(gameOverMessage, (800 - messageWidth) / 2, 200);

            // Draw restart message
            graphics.setColor(Color.ORANGE);
            graphics.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartMessage = "Press 'R' to restart the game.";
            int restartWidth = graphics.getFontMetrics().stringWidth(restartMessage);
            graphics.drawString(restartMessage, (800 - restartWidth) / 2, 250);

            // Draw current score
            graphics.setColor(Color.ORANGE);
            graphics.setFont(new Font("Arial", Font.PLAIN, 20));
            String scoreMessage = "Score: " + score.getScore();
            int scoreWidth = graphics.getFontMetrics().stringWidth(scoreMessage);
            graphics.drawString(scoreMessage, (800 - scoreWidth) / 2, 300);

            // Draw latest try list
            int latestRunsY = 350; // Y-coordinate for latest runs list
            int listX = 150; // X-coordinate for the list

            graphics.setColor(Color.ORANGE);
            graphics.setFont(new Font("Arial", Font.BOLD, 20));
            graphics.drawString("Latest Try:", listX, latestRunsY);
            latestRunsY += 20; // Move down for the list

            // Draw latest 10 tries list in reverse order
            List<Integer> latestRuns = scoreManager.getLatestRuns();
            List<Integer> latestScores = scoreManager.getLatestScores(); // Get latest scores
            for (int i = Math.max(0, latestRuns.size() - 1); i >= 0; i--) {
                int run = latestRuns.get(i);
                int score = latestScores.get(i); // Get latest score for the current run
                String runInfo = "Run " + run + ": " + score;
                graphics.drawString(runInfo, listX, latestRunsY);
                latestRunsY += 20; // Move down for the next run
            }
            // Draw high scores with corresponding names
            // Get the highest scores and corresponding names
            List<Integer> highestScores = scoreManager.getHighestScores();
            List<Integer> highestScoresRuns = scoreManager.getHighestScoresRuns();
            Map<Integer, String> highestScoresNames = scoreManager.getHighestScoresNames();

            int highScoreY = 350; // Y-coordinate for high score list
            int highScoreX = 500; // X-coordinate for the list

            graphics.setColor(Color.ORANGE);
            graphics.setFont(new Font("Arial", Font.BOLD, 20));
            graphics.drawString("Highest Scores:", highScoreX, highScoreY);
            highScoreY += 20; // Move down for the list

            // Draw each high score with the corresponding name
            for (int i = 0; i < highestScores.size(); i++) {
                int score = highestScores.get(i);
                int run = highestScoresRuns.get(i);
                String playerName = highestScoresNames.get(score); // Get the name corresponding to the score
                if (playerName != null) {
                    // Draw name and score with two dots in between
                    String scoreInfo = playerName + " : " + score;
                    graphics.drawString(scoreInfo, highScoreX, highScoreY);
                    highScoreY += 20; // Move down for the next score
                }
            }
        }
    }

    public void restartGame(GameBoard board) {
        if (gameOver) {
            // Reset game state
            gameOver = false;
            balls.clear();
            score.restart(); // Reset the score

            // Reinitialize game components
            balls.clear();
            bat = new Bat(400, 550, 100, 10, balls);
            boxes = new SquareCollection(balls, score, scoreManager);

            // Recreate initial ball
            int initialX = board.getPreferredSize().width / 2 - Const.BallSize / 2;
            int initialY = board.getPreferredSize().height / 2 - Const.BallSize / 2;
            balls.add(new Ball(initialX, initialY, Const.BallSize, Const.BallSize));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_R) {
            restartGame(board); // Call restartGame() when 'r' key is pressed
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // ActionListener for the Cancel button
    private ActionListener cancelActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false); // Make the JFrame invisible
            frame.dispose(); // Dispose the JFrame to release resources
        }
    };
    
    // ActionListener for the Submit button
    private ActionListener submitActionListener = new ActionListener() { 	
        @Override
        public void actionPerformed(ActionEvent e) {
            String playerName = nameField.getText().trim();
            if (playerName.length() != 3) {
                JOptionPane.showMessageDialog(null, "Please enter a 3-letter name.");
            } else {
                scoreManager.addScore(score.getScore(), playerName);
                nameEntered = true; // Set nameEntered to true after a valid name is entered
                frame.dispose();
            }
        }
    };
}
