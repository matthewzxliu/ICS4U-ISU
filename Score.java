/*
 * INTRODUCTORY COMMENTS:
 *
 * NAME: Jeevesh Balendra
 *
 * DATE: January 21st, 2024
 *
 * DESCRIPTION: This is the class for score objects.
 */

// SCORE OBJECT CLASS
public class Score implements Comparable<Score>{

    // Variables
    private int score;
    private String name;

    // Constructor
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Getters
    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }

    // Comparable: natural sorting order by their score descending
    // parameter: score object to compare current with
    // return int to show how the scores compare
    public int compareTo(Score other) {
        return other.score - this.score;
    }
}