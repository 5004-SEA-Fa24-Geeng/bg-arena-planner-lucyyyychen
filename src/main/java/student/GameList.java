package student;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashSet;

public class GameList implements IGameList {
    // Assume a no parameter constructor is provided for the GameList.
    // IGameList list = new GameList();
    // return values in Case Insensitive ascending order of game Name for any method
    // returns a list of games or strings.

    private Set<String> listOfGames;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        this.listOfGames = new HashSet<>();
    }


    /**
     * Gets the contents of a list, as list of names (Strings) in ascending order
     * ignoring case.
     *
     * @return the list of game names in ascending order ignoring case.
     */
    @Override
    public List<String> getGameNames() {
        // TODO Auto-generated method stub

        // Convert the Set of game names to a List and sort them case-insensitively
        List<String> listVersionGames = List.copyOf(listOfGames);
        return listVersionGames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER) // Sort in case-insensitive order
                .collect(Collectors.toList()); // Collect into a List
        }



    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'count'");
        return listOfGames.size();
    }

    @Override
    public void saveGame(String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveGame'");
    }

    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered
                .sorted(Comparator.comparing(game -> game.getName().toLowerCase())) // Ensure case-insensitive order
                .collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            throw new IllegalArgumentException("No games available to add.");
        }

        if (str.equalsIgnoreCase(ADD_ALL)) {
            // Add all games from the filtered list
            filteredList.forEach(game -> listOfGames.add(game.getName()));
            return;
        }

        // Check if the input is a valid game name
        Optional<BoardGame> gameByName = filteredList.stream()
                .filter(game -> game.getName().equalsIgnoreCase(str))
                .findFirst();

        if (gameByName.isPresent()) {
            // Add the game name to the list if it matches
            listOfGames.add(gameByName.get().getName());
            return;
        }

        // Try parsing the input as a number or range
        Pattern pattern = Pattern.compile("^(\\d+)(?:-(\\d+))?$"); // Matches "1" or "1-5"
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            int startIndex = Integer.parseInt(matcher.group(1)) - 1; // Convert to zero-based index
            int endIndex = (matcher.group(2) != null) ? Integer.parseInt(matcher.group(2)) - 1 : startIndex;

            if (startIndex < 0 || endIndex >= filteredList.size() || startIndex > endIndex) {
                throw new IllegalArgumentException("Invalid range: out of bounds or incorrectly formatted.");
            }

            for (int i = startIndex; i <= endIndex; i++) {
                listOfGames.add(filteredList.get(i).getName());
            }
            return;
        }

        // If no valid format or match is found, add the string as a game name directly
        listOfGames.add(str);
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFromList'");
    }


}
