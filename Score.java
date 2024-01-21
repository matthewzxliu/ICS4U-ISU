public class Score implements Comparable<Score>{
    private int score;
    private String name;
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }

    public int compareTo(Score other) {
        return other.score - this.score;
    }
}