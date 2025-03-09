package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class GameListTest {
    private GameList gameList;
    public Set<BoardGame> games;
    private Set<String> listOfGames;

    @BeforeEach
    void setUp() {
        gameList = new GameList();
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));

    }

    @Test
    void testGetGameNames_Empty() {
        assertTrue(gameList.getGameNames().isEmpty(), "Game list is still empty.");
    }

    @Test
    void testGetGameNames_NotEmpty() {
        // converts the Set<BoardGame> `games` collection into a Stream<BoardGame> to be passed to addToList()
        gameList.addToList("all", games.stream());
        List<String> gameNames = gameList.getGameNames();
        assertNotNull(gameNames, "Game names list is not null");
        List<String> expectedNames = Arrays.asList("17 days", "Chess", "Go", "Go Fish", "golang", "GoRami", "Monopoly", "Tucano");
        assertEquals(expectedNames, gameNames);
    }

    @Test
    void clear() {
        gameList.addToList("all", games.stream());
        gameList.clear();
        assertEquals(0, gameList.count());
    }

    @Test
    void count() {
        gameList.addToList("all", games.stream());
        assertEquals(8, gameList.count());
    }

    @Test
    void testSaveGame() throws IOException {
        IGameList list1 = new GameList();

        // Add games to list
        list1.addToList("1-3", games.stream()); // Assume this method exists to add games

        // Create a temporary file and save to the file
        File tempFile = File.createTempFile("test_games", ".txt");
        String filename = tempFile.getAbsolutePath();
        list1.saveGame(filename);

        assertEquals(List.of("17 days", "Chess", "Go"), Files.readAllLines(tempFile.toPath()));

        // Cleanup: Delete temp file after test
        tempFile.deleteOnExit();
    }

    // If a single name is specified, that takes priority.
    @Test
    void testAddSingleGameToListByName() {
        // String str, Stream<BoardGame> filtered
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.addToList("Just a game", games.stream());
        assertEquals(List.of("17 days", "Chess", "Go", "Go Fish",
                        "golang", "GoRami", "Just a game", "Monopoly", "Tucano"),
                list1.getGameNames());
    }

    // If the input is an existing game name,
    // then the list remains the same.
    @Test
    void testAddToListWithExistingName() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.addToList("Chess", games.stream());
        assertEquals(List.of("17 days", "Chess", "Go", "Go Fish",
                        "golang", "GoRami", "Monopoly", "Tucano"),
                list1.getGameNames());
    }


    // If "all" is specified,
    // all games in the filtered collection should be added to the list.
    @Test
    void testAddingAllToList() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        assertEquals(List.of("17 days", "Chess", "Go", "Go Fish",
                        "golang", "GoRami", "Monopoly", "Tucano"),
                list1.getGameNames());
    }

    // use a number such as 1
    // which would indicate game 1 from the current filtered list added to the list.
    @Test
    void testAddSingleGameToListByIndex() {
        // String str, Stream<BoardGame> filtered
        IGameList list1 = new GameList();
        list1.addToList("1", games.stream());
        assertEquals(List.of("17 days"), list1.getGameNames());
    }

    // if 1-5 was presented,
    // it is assumed that games 1 through 5 should be added to the list
    @Test
    void addRangeOfGamesToList() {
        IGameList list1 = new GameList();
        list1.addToList("1-3", games.stream());
        assertEquals(List.of("17 days", "Chess", "Go"), list1.getGameNames());
    }

    // 1-1 type formatting is allowed, and treated as just adding a single game.
    @Test
    void addSingleGameToListByRange() {
        IGameList list1 = new GameList();
        list1.addToList("1-1", games.stream());
        assertEquals(List.of("17 days"), list1.getGameNames());
    }

    // Invalid Range
    @Test
    void testAddToListWithInvalidRange() {
        IGameList list1 = new GameList();
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> list1.addToList("1-10", games.stream()));
        assertEquals("Invalid input: out of range", e.getMessage());
    }

    // Invalid games: empty stream
    @Test
    void testAddToListWithEmptyList() {
        IGameList list1 = new GameList();
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> list1.addToList("all", Stream.empty()));
        assertEquals("No games to be added.", e.getMessage());
    }



    // If a single name is specified, that takes priority.
    @Test
    void testRemoveSingleGameByName() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.removeFromList("17 days");
        assertEquals(List.of("Chess", "Go", "Go Fish",
                        "golang", "GoRami", "Monopoly", "Tucano"),
                list1.getGameNames());
    }


    // If "all" is specified,
    // all games in the filtered collection should be added to the list.
    @Test
    void testRemoveWholeList() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.removeFromList("all");
        assertEquals(0, list1.count());
    }

    // use a number such as 1
    // which would indicate game 1 from the current filtered list added to the list.
    @Test
    void testRemoveFromListByIndex() {
        // String str, Stream<BoardGame> filtered
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.removeFromList("1");
        assertEquals(List.of("Chess", "Go", "Go Fish",
                        "golang", "GoRami",  "Monopoly", "Tucano"),
                list1.getGameNames());
    }

    // if 1-5 was presented,
    // it is assumed that games 1 through 5 should be added to the list
    @Test
    void testRemoveFromListByRange() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.removeFromList("1-3");
        assertEquals(List.of( "Go Fish", "golang", "GoRami", "Monopoly", "Tucano"),
                list1.getGameNames());
    }

    // 1-1 type formatting is allowed, and treated as just adding a single game.
    @Test
    void testRemoveFromListByClosedRange() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.removeFromList("1-1");
        assertEquals(List.of("Chess", "Go", "Go Fish",
                        "golang", "GoRami", "Monopoly", "Tucano"),
                list1.getGameNames());
    }

    // Invalid Range
    @Test
    void testRemoveFromListWithInvalidRange() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> list1.removeFromList("1-10"));
        assertEquals("Invalid input: out of range", e.getMessage());
    }

    // Invalid games: empty stream
    @Test
    void testRemoveFromListWithEmptyList() {
        IGameList list1 = new GameList();
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> list1.removeFromList("all"));
        assertEquals("No games to be removed.", e.getMessage());
    }
}