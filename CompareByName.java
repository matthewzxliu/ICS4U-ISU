import java.util.Comparator;

public class CompareByName implements Comparator<Score> {
    public int compare(Score score1, Score score2) {
        // Compare names alphabetically
        return score1.getName().compareTo(score2.getName());
    }
}