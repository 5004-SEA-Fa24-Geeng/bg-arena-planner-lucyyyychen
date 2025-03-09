package student;


import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    Set<BoardGame> games;



    public Planner(Set<BoardGame> games) {
        this.games = games;
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented constructor 'Planner'");
    }

    @Override
    // filter can be multiple filters seperated by comas
    public Stream<BoardGame> filter(String filter) {
        // return Stream<BoardGame>;
        // "name == Go"
        // now just think of the filter only has one filter
        Stream<BoardGame> filteredStream = filterSingle(filter, games.stream());
        // do .... to stream()

        return filteredStream;
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

    private Stream<BoardGame> filterSingle(String filter, Stream<BoardGame> filteredGames) {
        // getting the attributes to work on: Enum Operations
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return filteredGames;
        }

        filter = filter.replaceAll(" ", "");
        // parsing the filter in a string to different filters
        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredGames;
        }

        GameData column;
        try {
            column = GameData.fromString(parts[0]);
        } catch (IllegalArgumentException o) {
            return filteredGames;
        }
        // more work to file the games...

        String value;
        try {
            value = parts[1].trim();
        } catch (IllegalArgumentException o) {
            return filteredGames;
        }

        System.out.println(operator);
        System.out.println(column);
        System.out.println(value);
        // Filters.filter(boardGame, column, operator, value)
        // Stream<BoardGame> filteredGames
        List<BoardGame> filteredGameList = filteredGames
                .filter(game -> Filters.filter(game, column, operator, value))
                .toList();

        return filteredGameList.stream();
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }


}
