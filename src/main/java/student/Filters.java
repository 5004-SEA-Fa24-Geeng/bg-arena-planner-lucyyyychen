package student;

public final class Filters {
    private Filters() { }

    /**
     * Filters a {@link BoardGame} object based on the specified column, operation, and value.
     *
     * @param game the {@link BoardGame} object to be filtered
     * @param column the {@link GameData} column to be filtered (e.g., NAME, ID, RATING)
     * @param op the {@link Operations} operation (e.g., EQUALS, GREATER_THAN)
     * @param value the value to compare against
     * @return if the game passes the filter (boolean)
     */
    public static boolean filter(BoardGame game, GameData column,
                                 Operations op, String value) {
        switch (column) {
            case NAME:
                // filter the name
                // BoardGame game is an object
                // ex. "name == Go" -> column = name, op = "==", value = "Go"
                return filterString(game.getName(), op, value);
            case ID:
                return filterInt(game.getId(), op, value);
            case RATING:
                return filterDouble(game.getRating(), op, value);
            case DIFFICULTY:
                return filterDouble(game.getDifficulty(), op, value);
            case RANK:
                return filterInt(game.getRank(), op, value);
            case MIN_PLAYERS:
                return filterInt(game.getMinPlayers(), op, value);
            case MAX_PLAYERS:
                return filterInt(game.getMaxPlayers(), op, value);
            case MIN_TIME:
                return filterInt(game.getMinPlayTime(), op, value);
            case MAX_TIME:
                return filterInt(game.getMaxPlayTime(), op, value);
            case YEAR:
                return filterInt(game.getYearPublished(), op, value);
            default:
                return false;  // false means not gonna include the game
        }
        // return...
    }

    /**
     * Filters a string value based on the specified operation and value.
     *
     * @param gameData the string value of the game data to be filtered
     * @param op the {@link Operations} operation (ex, EQUALS, CONTAINS)
     * @param value the value to compare against
     * @return if the string matches the filter criteria (boolean)
     */
    public static boolean filterString(String gameData, Operations op, String value) {
        switch (op) {
            case EQUALS:
                return gameData.equals(value);
            case NOT_EQUALS:
                return !gameData.equals(value);
            case CONTAINS:
                return gameData.contains(value);
            default:
                return false;
        }
    }

    /**
     * Filters an integer value based on the specified operation and value.
     *
     * @param gameData the integer value of the game data to be filtered
     * @param op the {@link Operations} operation (ex, GREATER_THAN, LESS_THAN)
     * @param value the value to compare against
     * @return if the integer matches the filter criteria (boolean)
     */
    public static boolean filterInt(int gameData, Operations op, String value) {
        int parsedValue = Integer.parseInt(value);
        switch (op) {
            case GREATER_THAN:
                return gameData > parsedValue;
            case LESS_THAN:
                return gameData < parsedValue;
            case GREATER_THAN_EQUALS:
                return gameData >= parsedValue;
            case LESS_THAN_EQUALS:
                return gameData <= parsedValue;
            case EQUALS:
                return gameData == parsedValue;
            case NOT_EQUALS:
                return gameData != parsedValue;
            default:
                return false;
        }
    }

    /**
     * Filters a double value based on the specified operation and value.
     *
     * @param gameData the double value of the game data to be filtered
     * @param op the {@link Operations} operation (ex. GREATER_THAN, LESS_THAN)
     * @param value the value to compare against
     * @return if the double matches the filter criteria (boolean)
     */
    public static boolean filterDouble(double gameData, Operations op, String value) {
        double parsedValue = Double.parseDouble(value);
        switch (op) {
            case GREATER_THAN:
                return gameData > parsedValue;
            case LESS_THAN:
                return gameData < parsedValue;
            case GREATER_THAN_EQUALS:
                return gameData >= parsedValue;
            case LESS_THAN_EQUALS:
                return gameData <= parsedValue;
            case EQUALS:
                return gameData == parsedValue;
            case NOT_EQUALS:
                return gameData != parsedValue;
            default:
                return false;
        }
    }
}
