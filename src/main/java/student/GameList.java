package student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    /**
     * A set that holds the list of game names.
     * The set stores game names in an unordered collection.
     */
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
        List<String> listVersionGames = new ArrayList<>(listOfGames);
        listVersionGames.sort(String.CASE_INSENSITIVE_ORDER); // Sort in case-insensitive order
        return listVersionGames;
        }



    @Override
    public void clear() {
        // TODO Auto-generated method stub
       this.listOfGames = new HashSet<>();
    }

    @Override
    public int count() {
        return listOfGames.size();
    }

    @Override
    public void saveGame(String filename) {
        // Convert the Set of game names to a List and sort them case-insensitively
        List<String> listVersionGames = new ArrayList<>(listOfGames);
        listVersionGames.sort(String.CASE_INSENSITIVE_ORDER); // Sort in case-insensitive order

        // Create the parent directory if it doesn't exist
        File file = new File(filename);
        File parentDirectory = file.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs(); // Create the directory, including any necessary but nonexistent parent directories
        }

        // Use FileWriter
        // Reference: https://www.geeksforgeeks.org/filewriter-class-in-java/
        try (FileWriter writer = new FileWriter(filename)) {
            for (String game : listVersionGames) {
                writer.write(game + System.lineSeparator());
            }

        } catch (IOException e) {
            System.out.println("An error occurred while writing"
                    + " to the file: " + e.getMessage());
        }
    }


    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
//             * @param str      the string to parse and add games to the list.
//             * @param filtered the filtered list to use as a basis for adding.
        // Ensure the list of games is sorted case-insensitively
//        listOfGames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        // Convert the input Stream to a List
        List<BoardGame> filteredList = filtered
                // .sorted((game1, game2) -> game1.getName().toLowerCase().compareTo(game2.getName().toLowerCase()))
                .sorted(Comparator.comparing(game -> game.getName().toLowerCase()))
                .toList();

        // Check invalid input: empty stream
        if (filteredList.isEmpty()) {
            throw new IllegalArgumentException("No games to be added.");
        }

        // If "all" is specified,
        // all games in the filtered collection should be added to the list.
        if (str.equalsIgnoreCase(ADD_ALL)) {
            filteredList.forEach(game -> listOfGames.add(game.getName()));
            return;
        }

        // Handling input as a number or range by Regex
        // Reference: https://www.w3schools.com/java/java_regex.asp
        // both "1" and "1-5" match
        // without `?:`, "1-5" does not match
        // without the second `?`, "1" does not match
        Pattern pattern = Pattern.compile("^(\\d+)(?:-(\\d+))?$");
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            // Convert a string with number(s) to zero-based index
            int startIndex = Integer.parseInt(matcher.group(1)) - 1;
            // if it's a range like 1-5, then extract 5 as endIndex
            // if it's just a number like 1, then set 1 as endIndex
            int endIndex = (matcher.group(2) != null) ? Integer.parseInt(matcher.group(2)) - 1 : startIndex;

            if (startIndex < 0 || endIndex >= filteredList.size() || startIndex > endIndex) {
                throw new IllegalArgumentException("Invalid input: out of range");
            }

            for (int i = startIndex; i <= endIndex; i++) {
                listOfGames.add(filteredList.get(i).getName());
            }
            return;
        }

        // If not matching "all" or a number or a range, add the string as a game name directly
        listOfGames.add(str);
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub

        // Check current list of games: empty list
        if (listOfGames.isEmpty()) {
            throw new IllegalArgumentException("No games to be removed.");
        }

        // If all is provided, then clear should be called.
        if (str.equalsIgnoreCase(ADD_ALL)) {
            listOfGames.clear();
        }


        // str is a number or a range
        Pattern pattern = Pattern.compile("^(\\d+)(?:-(\\d+))?$");
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            // Convert a string with number(s) to zero-based index
            int startIndex = Integer.parseInt(matcher.group(1)) - 1;
            // if it's a range like 1-5, then extract 5 as endIndex
            // if it's just a number like 1, then set 1 as endIndex
            int endIndex = (matcher.group(2) != null) ? Integer.parseInt(matcher.group(2)) - 1 : startIndex;
            // check if the range is out of bound
            if (startIndex < 0 || endIndex >= listOfGames.size() || startIndex > endIndex) {
                throw new IllegalArgumentException("Invalid input: out of range");
            }

            // Convert HashSet listOfGames to List for indexed access
            List<String> listCopy = new ArrayList<>(listOfGames);
            // Sort the list
            Collections.sort(listCopy);
            for (int i = startIndex; i <= endIndex; i++) {
                listOfGames.remove(listCopy.get(i));
            }
            return;
        }

        // If not matching "all" or a number or a range,
        // then if it's an existing name,  remove the string directly
        // get all game names

        Optional<String> gameName = listOfGames.stream()
                .filter(game -> game.equals(str))
                .findFirst();

        if (gameName.isPresent()) {
            listOfGames.remove(str);
        }

    }
}
