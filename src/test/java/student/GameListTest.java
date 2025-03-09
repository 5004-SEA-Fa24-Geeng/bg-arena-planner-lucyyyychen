package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }

    @Test
    void count() {
        gameList.addToList("all", games.stream());
        assertEquals(8, gameList.count());
    }

    @Test
    void saveGame() {
    }

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



    @Test
    void testAddingAllToList() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        assertEquals(List.of("17 days", "Chess", "Go", "Go Fish",
                        "golang", "GoRami", "Monopoly", "Tucano"),
                list1.getGameNames());
    }

    @Test
    void testAddSingleGameToListByIndex() {
        // String str, Stream<BoardGame> filtered
        IGameList list1 = new GameList();
        list1.addToList("1", games.stream());
        assertEquals(List.of("17 days"), list1.getGameNames());
    }

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

//    @Test
//    void removeFromList() {
//    }
}