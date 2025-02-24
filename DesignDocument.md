# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)



### Provided Code

Provide a class diagram for the provided code as you read through it.  
For the classes you are adding, you will create them as a separate diagram, 
so for now, you can just point towards the interfaces for the provided code diagram.

```mermaid
classDiagram
    BGArenaPlanner ..> IPlanner : "uses"
    BGArenaPlanner ..> IGameList : "uses"
    
    BGArenaPlanner ..> GamesLoader : "uses"
    BGArenaPlanner ..> ConsoleApp : "uses"
    
    ConsoleApp ..> IGameList : "uses"
    ConsoleApp ..> IPlanner : "uses"


    
    IGameList ..> BoardGame : "uses"
    GamesLoader ..> BoardGame : "uses"
    GamesLoader ..> GameData : "uses"
    
    
    


    class BGArenaPlanner{
        <<final>>
        - DEFAULT_COLLECTION$: String
        - BGArenaPlanner()
        + main(String[] args)$: void
    }
    class ConsoleApp{
        - IN: Scanner
        - DEFAULT_FILENAME: String
        - RND: Random
        - current: Scanner
        - gameList: IGameList
        - planner: IPlanner
        
        + start(): void
        - randomNumber(): void
        - processHelp(): void
        - processFilter(): void
        - printFilterStream(Stream<BoardGame> games, GameData sortON): void
        - processListCommands(): void
        - printCurrentList(): void
        - nextCommand(): ConsoleText
        - remainder(): String
        - getInput(String format, Object ...)$: String
        - printOutput(String format, Object...)$: void
        - ConsoleText: enum
            - CTEXT$: Properties
            + toString(): String
            + fromString(String str)$: ConsoleText
    }
    class GamesLoader{
        - DELIMITER$: String
        - GamesLoader(): ...
        + loadGamesFile(String filename)$: Set<BoardGame>
        - toBoardGame(String line, Map<GameData, Integer> columnMap)$: BoardGame
        - processHeader(String header)$: Map<GameData, Integer>
    }
    class BoardGame{
        - name: String
        - id: int
        - minPlayers: int
        - maxPlayers: int
        - maxPlayTime: int
        - minPlayTime: int
        - difficulty: double
        - rank: int
        - averageRating: double
        - yearPublished: int
        + getName(): String
        + getId(): int
        + getMinPlayers(): int
        + getMaxPlayers(): int
        + getMaxPlayTime(): int
        + getMinPlayTime(): int
        + getDifficulty(): double
        + getRank(): int
        + getRating(): double
        + getYearPublished(): int
        + toStringWithInfo(GameData col): String
        + toString(): String
        + equals(Object obj): boolean
        + hashCode(): int
        + main(String[] args)$: void
    }
    class IGameList {
        <<interface>>
        ADD_ALL: String
        getGameNames(): List<String>
        clear(): void
        count(): int
        saveGame(String filename): void
        addToList(String str, Stream<BoardGame> filtered): void
        removeFromList(String str): void
    }
    class IPlanner {
        <<interface>>
        filter(String filter): Stream<BoardGame>
        filter(String filter, GameData sortOn): Stream<BoardGame>
        filter(String filter, GameData sortOn, boolean ascending): Stream<BoardGame>
        reset():void
    }
    class GameData{
        <<enumeration>>
        NAME("objectname")
        ID("objectid")
        RATING("average")
        DIFFICULTY("avgweight")
        RANK("rank")
        MIN_PLAYERS("minplayers")
        MAX_PLAYERS("maxplayers")
        MIN_TIME("minplaytime")
        MAX_TIME("maxplaytime")
        YEAR("yearpublished")

        - columnName: String
        + getColumnName(): String
        + fromColumnName(String columnName)$: GameData
        + fromString(String name)$: GameData
    }
    

```

### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 

```mermaid

classDiagram

    IPlanner  <|.. Planner : "implements"
    IGameList <|.. GameList : "implements"
    
    Planner ..> Filter : "uses"
    Planner ..> Sort : "uses"
    Planner ..> GameData : "uses"
    Filter ..> Operations : "uses"
    Sort ..> Operations : "uses"
    

    class IGameList {
        <<interface>>
        ADD_ALL: String
        getGameNames(): List<String>
        clear(): void
        count(): int
        saveGame(String filename): void
        addToList(String str, Stream<BoardGame> filtered): void
        removeFromList(String str): void
    }
    class IPlanner {
        <<interface>>
        filter(String filter): Stream<BoardGame>
        filter(String filter, GameData sortOn): Stream<BoardGame>
        filter(String filter, GameData sortOn, boolean ascending): Stream<BoardGame>
        reset():void
    }
    class GameList {
        + getGameNames(): List<String>
        + clear(): void
        + count(): int
        + saveGame(String filename): void
        + addToList(String str, Stream<BoardGame> filtered): void
        + removeFromList(String str): void
    }
    class Planner {
        + filter(String filter): Stream<BoardGame>
        + filter(String filter, GameData sortOn): Stream<BoardGame>
        + filter(String filter, GameData sortOn, boolean ascending): Stream<BoardGame>
        + reset(): void
    }

    class Filter{
        - filterText: String
        + filterStream(String filterText): Stream<List>
    }
    class Sort{
        - sortOn: String
        - isAscending: boolean
        + sortStream(String sortOn, boolean isAscending): Stream<List>
    }
    class Operations{
        <<enumeration>>
        EQUALS("==")
        NOT_EQUALS("!=")
        GREATER_THAN(">")
        LESS_THAN("<")
        GREATER_THAN_EQUALS(">=")
        LESS_THAN_EQUALS("<=")
        CONTAINS("~=")

        - operator: String
        + getOperator(): String
        + fromOperator(String operator)$: Operations
        + getOperatorFromStr(String str)$: Operations

    }
    class GameData{
        <<enumeration>>
        NAME("objectname")
        ID("objectid")
        RATING("average")
        DIFFICULTY("avgweight")
        RANK("rank")
        MIN_PLAYERS("minplayers")
        MAX_PLAYERS("maxplayers")
        MIN_TIME("minplaytime")
        MAX_TIME("maxplaytime")
        YEAR("yearpublished")

        - columnName: String
        + getColumnName(): String
        + fromColumnName(String columnName)$: GameData
        + fromString(String name)$: GameData
    }
```

#### FULL UML Diagram
```mermaid
---
title: Board Game Area - Game Planner
---
classDiagram
    BGArenaPlanner ..> IPlanner : "uses"
    BGArenaPlanner ..> IGameList : "uses"
    
    BGArenaPlanner ..> GamesLoader : "uses"
    BGArenaPlanner ..> ConsoleApp : "uses"
    ConsoleApp ..> IGameList : "uses"
    ConsoleApp ..> IPlanner : "uses"

    IPlanner  <|.. Planner : "implements"
    IGameList <|.. GameList : "implements"
    
    IGameList ..> BoardGame : "uses"
    GamesLoader ..> BoardGame : "uses"

    Planner ..> Filter : "uses"
    Planner ..> Sort : "uses"
    Planner ..> GameData : "uses"
    Filter ..> Operations : "uses"
    Sort ..> Operations : "uses"

    GamesLoader ..> GameData : "uses"
    
    class BoardGame{
        - name: String
        - id: int
        - minPlayers: int
        - maxPlayers: int
        - maxPlayTime: int
        - minPlayTime: int
        - difficulty: double
        - rank: int
        - averageRating: double
        - yearPublished: int
        + getName(): String
        + getId(): int
        + getMinPlayers(): int
        + getMaxPlayers(): int
        + getMaxPlayTime(): int
        + getMinPlayTime(): int
        + getDifficulty(): double
        + getRank(): int
        + getRating(): double
        + getYearPublished(): int
        + toStringWithInfo(GameData col): String
        + toString(): String
        + equals(Object obj): boolean
        + hashCode(): int
        + main(String[] args)$: void
    }
    class BGArenaPlanner{
        <<final>>
        - DEFAULT_COLLECTION$: String
        - BGArenaPlanner()
        + main(String[] args)$: void
    }
    class ConsoleApp{
        - IN: Scanner
        - DEFAULT_FILENAME: String
        - RND: Random
        - current: Scanner
        - gameList: IGameList
        - planner: IPlanner
        
        + start(): void
        - randomNumber(): void
        - processHelp(): void
        - processFilter(): void
        - printFilterStream(Stream<BoardGame> games, GameData sortON): void
        - processListCommands(): void
        - printCurrentList(): void
        - nextCommand(): ConsoleText
        - remainder(): String
        - getInput(String format, Object ...)$: String
        - printOutput(String format, Object...)$: void
        - ConsoleText: enum
            - CTEXT$: Properties
            + toString(): String
            + fromString(String str)$: ConsoleText
    }
    class GamesLoader{
        - DELIMITER$: String
        - GamesLoader(): ...
        + loadGamesFile(String filename)$: Set<BoardGame>
        - toBoardGame(String line, Map<GameData, Integer> columnMap)$: BoardGame
        - processHeader(String header)$: Map<GameData, Integer>
    }
    class IGameList {
        <<interface>>
        ADD_ALL: String
        getGameNames(): List<String>
        clear(): void
        count(): int
        saveGame(String filename): void
        addToList(String str, Stream<BoardGame> filtered): void
        removeFromList(String str): void
    }
    class IPlanner {
        <<interface>>
        filter(String filter): Stream<BoardGame>
        filter(String filter, GameData sortOn): Stream<BoardGame>
        filter(String filter, GameData sortOn, boolean ascending): Stream<BoardGame>
        reset():void
    }
    class GameList {
        + getGameNames(): List<String>
        + clear(): void
        + count(): int
        + saveGame(String filename): void
        + addToList(String str, Stream<BoardGame> filtered): void
        + removeFromList(String str): void
    }
    class Planner {
        + filter(String filter): Stream<BoardGame>
        + filter(String filter, GameData sortOn): Stream<BoardGame>
        + filter(String filter, GameData sortOn, boolean ascending): Stream<BoardGame>
        + reset(): void
    }
    
    class GameData{
        <<enumeration>>
        NAME("objectname")
        ID("objectid")
        RATING("average")
        DIFFICULTY("avgweight")
        RANK("rank")
        MIN_PLAYERS("minplayers")
        MAX_PLAYERS("maxplayers")
        MIN_TIME("minplaytime")
        MAX_TIME("maxplaytime")
        YEAR("yearpublished")
        
        - columnName: String
        + getColumnName(): String
        + fromColumnName(String columnName)$: GameData
        + fromString(String name)$: GameData
    }
    class Operations{
        <<enumeration>>
        EQUALS("==")
        NOT_EQUALS("!=")
        GREATER_THAN(">")
        LESS_THAN("<")
        GREATER_THAN_EQUALS(">=")
        LESS_THAN_EQUALS("<=")
        CONTAINS("~=")

        - operator: String
        + getOperator(): String
        + fromOperator(String operator)$: Operations
        + getOperatorFromStr(String str)$: Operations

    }
    class Filter{
        - filterText: String
        + filterStream(String filterText): Stream<List>
    }
    class Sort{
        - sortOn: String
        - isAscending: boolean
        + sortStream(String sortOn, boolean isAscending): Stream<List>
    }
    
```



## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

12 Tests.

1. Test filter(String filter) in the Planner class
2. Test filter(String filter, GameData sortOn)  in the Planner class
3. Test filter(String filter, GameData sortOn, boolean ascending)  in the Planner class
4. Test reset() in the Planner class
5. Test getGameNames() in the GameList class
6. Test clear() in the GameList class
7. Test count() in the GameList class
8. Test saveGame(String filename) in the GameList class
9. Test addToList(String str, Stream filtered) in the GameList class
10. Test removeFromList(String str) in the GameList class
11. Test filterStream in the Filter Class
12. Test sortStream in the Sort class



## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 
