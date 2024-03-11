package Lab3;

import java.io.Serializable;
import java.util.*;

public class ScoreManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentRunNumber = 1;
    private List<Integer> latestRuns = new ArrayList<>();
    private List<Integer> latestRunsScores = new ArrayList<>();
    private List<Integer> highestScores = new ArrayList<>();
    private List<Integer> highestScoresRuns = new ArrayList<>();
    private List<Integer> latestScores = new ArrayList<>();
    private Map<Integer, String> highestScoresNames = new HashMap<>();
    private static final int MAX_LIST_SIZE = 10;

    public void addScore(int score, String playerName) {
        latestRuns.add(currentRunNumber);
        latestRunsScores.add(score);
        currentRunNumber++;

        if (highestScores.size() < MAX_LIST_SIZE) {
            // If the highest scores list is not full, simply add the new score
            highestScores.add(score);
            highestScoresRuns.add(currentRunNumber - 1);
            highestScoresNames.put(score, playerName); // Associate the name with the score
            sortScoresDescending();
        } else {
            // If the highest scores list is full, check if the new score qualifies to be added
            if (score > highestScores.get(highestScores.size() - 1)) {
                // Remove the lowest score
                highestScores.remove(highestScores.size() - 1);
                highestScoresRuns.remove(highestScoresRuns.size() - 1);
                highestScoresNames.remove(highestScores.get(highestScores.size() - 1));

                // Add the new score
                highestScores.add(score);
                highestScoresRuns.add(currentRunNumber - 1);
                highestScoresNames.put(score, playerName); // Associate the name with the score
                sortScoresDescending();
            }
        }

        limitListSize(latestRuns, MAX_LIST_SIZE);
        limitListSize(latestRunsScores, MAX_LIST_SIZE);
        latestScores.add(score);
        limitListSize(latestScores, MAX_LIST_SIZE);
    }


    public List<Integer> getHighestScores() {
        return highestScores;
    }

    public List<Integer> getHighestScoresRuns() {
        return highestScoresRuns;
    }

    public List<Integer> getLatestRuns() {
        return latestRuns;
    }

    public List<Integer> getLatestScores() {
        return latestScores;
    }

    public Map<Integer, String> getHighestScoresNames() {
        return highestScoresNames;
    }

    private void limitListSize(List<Integer> list, int maxSize) {
        while (list.size() > maxSize) {
            list.remove(0);
        }
    }

    private void sortScoresDescending() {
        ArrayList<ScoreRunPair> pairs = new ArrayList<>();
        for (int i = 0; i < highestScores.size(); i++) {
            pairs.add(new ScoreRunPair(highestScores.get(i), highestScoresRuns.get(i)));
        }
        Collections.sort(pairs);
        Collections.reverse(pairs);

        highestScores.clear();
        highestScoresRuns.clear();

        for (ScoreRunPair pair : pairs) {
            highestScores.add(pair.score);
            highestScoresRuns.add(pair.run);
        }
    }

    private class ScoreRunPair implements Comparable<ScoreRunPair>, Serializable {
        private static final long serialVersionUID = 1L;
        private int score;
        private int run;

        public ScoreRunPair(int score, int run) {
            this.score = score;
            this.run = run;
        }

        @Override
        public int compareTo(ScoreRunPair other) {
            return Integer.compare(score, other.score);
        }
    }
}
