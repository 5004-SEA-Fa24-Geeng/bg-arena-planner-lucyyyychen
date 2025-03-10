package student;

import java.util.Comparator;

public final class Sort {
    private Sort() { }
    /**
     * Returns a {@link Comparator} for sorting {@link BoardGame} objects based on the specified attribute and order.
     * The method selects the appropriate comparison logic based on the provided {@link GameData} column (e.g., NAME, ID, RATING, etc.)
     * and sorts the games either in ascending or descending order, depending on the {@code ascending} flag.
     *
     * @param sortOn the {@link GameData} attribute to sort by (e.g., NAME, ID, RATING, etc.)
     * @param ascending {@code true} for ascending order, {@code false} for descending order
     * @return a {@link Comparator} for sorting the games based on the specified criteria, or {@code null} if the column is invalid
     */
    public static Comparator<BoardGame> comparingOn(GameData sortOn, boolean ascending) {
        Comparator<BoardGame> comparator = null;

        switch (sortOn) {
            case NAME:
                comparator = (game1, game2) -> game1.getName().compareTo(game2.getName());
                break;
            case ID:
                comparator = (game1, game2) -> Integer.compare(game1.getId(), game2.getId());
                break;
            case RATING:
                comparator = (game1, game2) -> Double.compare(game1.getRating(), game2.getRating());
                break;
            case DIFFICULTY:
                comparator = (game1, game2) -> Double.compare(game1.getDifficulty(), game2.getDifficulty());
                break;
            case RANK:
                comparator = (game1, game2) -> Integer.compare(game1.getRank(), game2.getRank());
                break;
            case MIN_PLAYERS:
                comparator = (game1, game2) -> Integer.compare(game1.getMinPlayers(), game2.getMinPlayers());
                break;
            case MAX_PLAYERS:
                comparator = (game1, game2) -> Integer.compare(game1.getMaxPlayers(), game2.getMaxPlayers());
                break;
            case MIN_TIME:
                comparator = (game1, game2) -> Integer.compare(game1.getMinPlayTime(), game2.getMinPlayTime());
                break;
            case MAX_TIME:
                comparator = (game1, game2) -> Integer.compare(game1.getMaxPlayTime(), game2.getMaxPlayTime());
                break;
            case YEAR:
                comparator = (game1, game2) -> Integer.compare(game1.getYearPublished(), game2.getYearPublished());
                break;
            default:
                return null;  // if the column is invalid, return null (= no sorting)
        }

        // if ascending is false, reverse the comparator for descending order
        if (!ascending) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
}
