package Lab3;

import java.io.Serializable;
import java.util.*;
import javax.swing.*;

public class ScoreManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentRunNumber = 1;
    private DefaultListModel<Integer> latestRuns = new DefaultListModel<>();
    private DefaultListModel<Integer> latestRunsScores = new DefaultListModel<>();
    private DefaultListModel<Integer> highestScores = new DefaultListModel<>();
    private DefaultListModel<Integer> latestScores = new DefaultListModel<>();
    private Map<Integer, String> highestScoresNames = new HashMap<>();
    private static final int MAX_LIST_SIZE = 10;

    public void addScore(int score, String playerName) {
        latestRuns.addElement(currentRunNumber);
        latestRunsScores.addElement(score);
        currentRunNumber++;

        if (highestScores.getSize() < MAX_LIST_SIZE) {
            // If the highest scores list is not full, simply add the new score
            highestScores.addElement(score);
            highestScoresNames.put(score, playerName); // Associate the name with the score
            sortScoresDescending();
        } else {
            // If the highest scores list is full, check if the new score qualifies to be added
            if (score > highestScores.elementAt(highestScores.getSize() - 1)) {
                // Remove the lowest score
                highestScores.removeElementAt(highestScores.getSize() - 1);
                highestScoresNames.remove(highestScores.elementAt(highestScores.getSize() - 1));

                // Add the new score
                highestScores.addElement(score);
                highestScoresNames.put(score, playerName); // Associate the name with the score
                sortScoresDescending();
            }
        }

        limitListSize(latestRuns, MAX_LIST_SIZE);
        limitListSize(latestRunsScores, MAX_LIST_SIZE);
        latestScores.addElement(score);
    }



    public JList<Integer> getHighestScores() {
        return new JList<>(highestScores);
    }


    public JList<Integer> getLatestRuns() {
        return new JList<>(latestRuns);
    }

    public JList<Integer> getLatestScores() {
        return new JList<>(latestScores);
    }

    public Map<Integer, String> getHighestScoresNames() {
        return highestScoresNames;
    }

    private void limitListSize(DefaultListModel<Integer> listModel, int maxSize) {
        while (listModel.getSize() > maxSize) {
            listModel.remove(0);
        }
    }

    private void sortScoresDescending() {
        ArrayList<ScoreRunPair> pairs = new ArrayList<>();
        for (int i = 0; i < highestScores.getSize(); i++) {
            pairs.add(new ScoreRunPair(highestScores.getElementAt(i)));
        }
        Collections.sort(pairs);
        Collections.reverse(pairs);

        highestScores.removeAllElements();

        for (ScoreRunPair pair : pairs) {
            highestScores.addElement(pair.score);
        }
    }

    private class ScoreRunPair implements Comparable<ScoreRunPair>, Serializable {
        private static final long serialVersionUID = 1L;
        private int score;

        public ScoreRunPair(int score) {
            this.score = score;
        }

        @Override
        public int compareTo(ScoreRunPair other) {
            return Integer.compare(score, other.score);
        }
    }
}
