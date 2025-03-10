package student;


import java.util.*;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    /**
     * A set of {@link BoardGame} objects representing the current collection of games in the planner.
     * This set allows for efficient storage and management of games, ensuring that duplicates are not allowed.
     */
    private Set<BoardGame> games;

    /**
     * A set of {@link BoardGame} objects representing the initial collection of games in the planner.
     * This set stores the original state of the games so that the planner can revert to it if necessary.
     */
    private Set<BoardGame> initialGames;


    /**
     * Constructs a new {@code Planner} with the specified set of games.
     * The initial set of games is saved in the {@code initialGames} set for future reference.
     *
     * @param games a set of {@link BoardGame} objects to be managed by the planner
     */
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

        // ensure the final stream is sorted after all filters are applied.
        return filteredStream.sorted(Comparator.comparing(game -> game.getName().toLowerCase()));
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
