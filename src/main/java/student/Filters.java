package student;

public final class Filters {
    private Filters() {}

    public static boolean filter(BoardGame game, GameData column,
                                 Operations op, String value) {
        switch (column) {
            case NAME:
                // filter the name
                // BoardGame game is an object
                // ex. "name == Go" -> column = name, op = "==", value = "Go"
                return filterString(game.getName(), op, value);
            case MAX_PLAYERS:
                // filter based on max-players
                return false;  // false means not gonna include the game
            default:
                return false;
        }
        // return...
    }

    public static boolean filterString(String gameData, Operations op, String value) {
        switch (op) {
            case EQUALS:
                return gameData.equals(value);
            default:
                return false;
        }
    }

    public static boolean filterNum(int gameData, Operations op, String value) {
        int value = Integer.parseInt(value);
        switch (op) {
            case EQUALS:
                // double something
            case GREATER_THAN:
        }
    }
}
