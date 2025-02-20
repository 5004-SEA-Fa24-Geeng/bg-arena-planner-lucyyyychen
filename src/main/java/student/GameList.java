package student;

import java.util.*;
import java.util.stream.Stream;
import java.util.HashSet;

public class GameList implements IGameList {
    Set<String> listOfGames;
    /**
     * Constructor for the GameList.
     */
    public GameList() {
//        throw new UnsupportedOperationException("Unimplemented constructor 'GameList'");
        listOfGames = new HashSet<>();
    }

    @Override
    public List<String> getGameNames() {
        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'getGameNames'");
        List<String> ListVersionGames = List.copyOf(listOfGames);
        return ListVersionGames;
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
        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'addToList'");
        // listOfGames = new HashSet<String>();
        List<BoardGame> filteredList = filtered.toList();
        BoardGame toAdd = filteredList.get(Integer.parseInt(str));
        listOfGames.add(toAdd.getName());

    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFromList'");
    }


}
