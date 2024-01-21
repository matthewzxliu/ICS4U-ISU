/*
 * INTRODUCTORY COMMENTS:
 *
 * NAME: Jeevesh Balendra
 *
 * DATE: January 21st, 2024
 *
 * DESCRIPTION: This is the class for sorting highscores.
 */

// IMPORT
import java.util.Comparator;

// COMPARATOR
public class CompareByName implements Comparator<Score> {
    public int compare(Score score1, Score score2) {
        // Compare names alphabetically
        return score1.getName().compareTo(score2.getName());
    }
}