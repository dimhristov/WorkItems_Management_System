package commands.list;

import com.wim.commands.contracts.Command;
import com.wim.commands.list.ListWorkItems;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Story;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.BugImpl;
import com.wim.models.implementation.StoryImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class ListWorkItems_Test {


    private ItemManagementRepository repository;
    private List<String> stepsToReproduce;
    private Team testTeam;
    private Board testBoard;
    private Bug testBug;
    private Command testCommand;


    @Before
    public void before () {
        repository = new ItemManagementRepositoryImpl();
        stepsToReproduce = List.of("First step", "Second step", "Third step");
        testTeam = new TeamImpl("Alpha");
        repository.addTeam("Alpha", testTeam);
        testBoard = new BoardImpl("BoardName");
        repository.getTeam("Alpha").getBoardList().add(testBoard);
        testBug = new BugImpl("TestBugName", "description test", "HIGH", "MAJOR", stepsToReproduce);
        repository.findBoard("BoardName", "Alpha").getWorkItems().add(testBug);
        testCommand = new ListWorkItems(repository);
    }


    @Test
    public void execute_should_ListAllWorkItems () {
        //Arrange
        List<String> testList = new ArrayList<>();
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName", "Alpha")
                                          .getWorkItems().get(0).getTitle(), "TestBugName");
    }
}
