package student;


import java.util.*;
import java.util.logging.Filter;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    private Set<BoardGame> games;
    private Set<BoardGame> initialGames;

    public Planner(Set<BoardGame> games) {
        this.games = games;
        this.initialGames = new HashSet<>(games);
    }

    @Override
    // filter can be multiple filters seperated by comas
    // Assumes the results are sorted in ascending order, and that the steam is sorted by the name
    // of the board game (GameData.NAME).
    public Stream<BoardGame> filter(String filter) {
        Stream<BoardGame> filteredStream = games.stream();

        // If the filter is empty, return all games sorted by name (case-insensitive)
        if (filter.trim().isEmpty()) {
            return games.stream()
                    .sorted(Comparator.comparing(game -> game.getName().toLowerCase()));
        }

        // Split filters by comma and store them to a string array
        String[] filters = filter.split(",");
        for (String s : filters) {
            filteredStream = filterSingle(s.trim(), filteredStream);
        }

        return filteredStream;
    }

    private Stream<BoardGame> filterSingle(String filter, Stream<BoardGame> filteredGames) {
        // extract the operator from the given filter, store it in `operator `
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return filteredGames;  // ignore invalid filters
        }

        // Ignore spaces
        filter = filter.replaceAll(" ", "");

        // parsing the filter in a string to different filters
        // ex. minPlayers>4
        // parts = [minPlayers, 4]
        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredGames;
        }

        // Ignore invalid column names
        GameData column;
        try {
            // parts[0] = "minPlayers"
            column = GameData.fromString(parts[0]);
        } catch (IllegalArgumentException o) {
            return filteredGames;
        }

        // parts[1] = "4"
        // trim() removes any leading or trailing spaces
        String value = parts[1].trim();

        // Apply the filtering and return as a stream
        return filteredGames.filter(game -> Filters.filter(game, column, operator, value));
    }

    @Override
    // Reference: https://www.geeksforgeeks.org/comparator-interface-java/
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // call filter(String filter, GameData sortOn, boolean ascending)
        // set boolean as true
        Stream<BoardGame> filteredSortedStream = filter(filter, sortOn, true);
        return filteredSortedStream;
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        // call filter(String filter) to get the filtered Stream
        Stream<BoardGame> filteredStream = filter(filter);

        // Initiate a comparator for sorting
        Comparator<BoardGame> comparator = Sort.comparingOn(sortOn, ascending);

        // If not matching any valid comparator, return the stream without sorting
        if (comparator == null) {
            return filteredStream;
        }

        // sort the filtered stream
        return filteredStream.sorted(comparator);
    }


    @Override
    public void reset() {
        // TODO Auto-generated method stub
        // set the games to its initial empty hash set
        games = new HashSet<>(initialGames);
    }



}
