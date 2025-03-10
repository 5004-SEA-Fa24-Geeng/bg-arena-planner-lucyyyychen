package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {

    private Planner planner;
    private Set<BoardGame> games;

    @BeforeEach
    void setUp() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
        planner = new Planner(games);
    }

    @Test
    // filter1: Stream<BoardGame> filter(String filter);
    // difficulty>9
    void testFilter1ByOneCondition() {
        List<String> result = planner.filter("difficulty>9")
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("Chess");
        assertEquals(expected, result);
    }

    @Test
        // filter1: Stream<BoardGame> filter(String filter);
        // name~=G
    void testFilter1ByNameContain() {
        List<String> result = planner.filter("name~=Fish")
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("Go Fish");
        assertEquals(expected, result);
    }

    @Test
        // filter1: Stream<BoardGame> filter(String filter);
        // name~=G
    void testFilter1ByNameContain2() {
        List<String> result = planner.filter("name~=G")
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("Go", "Go Fish", "golang", "GoRami");
        assertEquals(expected, result);
    }

    @Test
    // filter1: Stream<BoardGame> filter(String filter);
    void testFilter1ByTwoConditionsInTwoColumns() {
        List<String> result = planner.filter("minPlayers>4,maxPlayers<15")
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("GoRami", "Monopoly");
        assertEquals(expected, result);
    }

    @Test
    // filter1: Stream<BoardGame> filter(String filter);
    // Filter games with minPlayers > 4 AND minPlayers < 6
    void testFilter1ByTwoConditionsInOneColumns() {
        List<String> result = planner.filter("minPlayers>4,minPlayers<7")
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("GoRami", "Monopoly");
        assertEquals(expected, result);
    }

    @Test
    // filter1: Stream<BoardGame> filter(String filter);
    // Spaces should be ignored
    void testFilter1IgnoreSpaces() {
        List<String> result = planner.filter("minPlayers > 4")
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("GoRami", "Monopoly", "Tucano");
        assertEquals(expected, result);
    }

    @Test
    // filter1: Stream<BoardGame> filter(String filter);
    // case insensitive: name~=pandemic equals name~=PANDEMIC
    void testFilter1CaseInsensitive() {
        List<String> lowerResult = planner.filter("name~=pandemic")
                .map(BoardGame::getName)
                .toList();
        List<String> upperResult = planner.filter("name~=PANDEMIC")
                .map(BoardGame::getName)
                .toList();
        assertEquals(lowerResult, upperResult);
    }



//    @Test
//    // filter1: Stream<BoardGame> filter(String filter);
//    // the filter is invalid
//    // then the results should return the current filter sorted based on the sortOn column and in the
//    void testFilter1ByInvalidFilter() {
//        List<String> result = planner.filter("minPlayers>4extra")
//                .map(BoardGame::getName)
//                .toList();
//        List<String> expected = List.of("17 days", "Chess", "Go", "Go Fish", "golang", "GoRami", "Monopoly", "Tucano");
//        assertEquals(expected, result);
//    }



    @Test
    // Stream<BoardGame> filter(String filter, GameData sortOn);
    // filter the year, sort by year
    void testFilter2ByYear() {
        List<String> result = planner.filter("yearPublished>2004", GameData.YEAR)
                .map(BoardGame::getName)
                .toList();

        List<String> expected = List.of("17 days", "Chess", "Monopoly"); // 2005, 2006, 2007
        assertEquals(expected, result);
    }

    @Test
    // Stream<BoardGame> filter(String filter, GameData sortOn);
    void testFilter2ByMultipleConditions() {
        List<String> result = planner.filter("minPlayers>5,maxPlayers<15", GameData.MAX_PLAYERS)
                .map(BoardGame::getName)
                .toList();

        List<String> expected = List.of("GoRami", "Monopoly"); // MAX_PLAYERS: 6, 10
        assertEquals(expected, result);
    }


    @Test
    // Stream<BoardGame> filter(String filter, GameData sortOn);
    void testFilter2FilterByMinPlayersSortByRank() {
        List<String> result = planner.filter("minPlayers==2", GameData.RANK)
                .map(BoardGame::getName)
                .toList();
        // minPlayers = 2: "Chess", "Go", "Go Fish", "golang" (Rank: 700, 100, 200, 400)
        List<String> expected = List.of("Go", "Go Fish", "golang", "Chess"); // Rank: 100, 200, 400, 700
        assertEquals(expected, result);
    }

    @Test
    // Stream<BoardGame> filter(String filter, GameData sortOn);
    // the filter is empty (""),
    // then the results should return the current filter sorted based on the sortOn column and in the
    void testFilter2EmptyFilter() {
        List<String> result = planner.filter("", GameData.YEAR)
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("Go", "Go Fish", "GoRami", "golang", "Tucano", "17 days", "Chess", "Monopoly");
        assertEquals(expected, result);
    }

    @Test
    // Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending);
    void testFilter3() {
        List<String> result = planner.filter("yearPublished>2004", GameData.YEAR, false)
                .map(BoardGame::getName)
                .toList();
        List<String> expected = List.of("Monopoly", "Chess", "17 days"); // 2007, 2006, 2005
        assertEquals(expected, result);
    }


    @Test
    // Resets the collection to have no filters applied.
    void testReset() {
        // Initially apply some filters
        Stream<BoardGame> filteredStream = planner.filter("minPlayers > 4");
        List<String> filteredNames = filteredStream.map(BoardGame::getName).toList();
        List<String> expectedFilteredNames = List.of("GoRami", "Monopoly", "Tucano");
        assertEquals(expectedFilteredNames, filteredNames);

        // call reset to remove filters
        planner.reset();

        // After reset, the games should not be filtered, and all games should be returned
        List<String> allNames = planner.filter("").map(BoardGame::getName).toList();
        List<String> expectedAllNames = List.of("17 days", "Chess", "Go", "Go Fish", "golang", "GoRami", "Monopoly", "Tucano");

        // Assert that the list of games after reset matches the original list
        assertEquals(expectedAllNames, allNames);
    }

}