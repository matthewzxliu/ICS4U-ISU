// score object class

public class Score implements Comparable<Score>{

    // variables
    private int score;
    private String name;

    // constructor
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // getters
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