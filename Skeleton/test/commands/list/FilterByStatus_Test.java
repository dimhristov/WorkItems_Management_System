package commands.list;

import com.wim.commands.contracts.Command;
import com.wim.commands.list.FilterByStatus;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.*;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.BugImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class FilterByStatus_Test {

    private ItemManagementRepository repository;
    private List<String> stepsToReproduce;
    private Team testTeam;
    private Board testBoard;
    private Bug testBug;
    private Command testCommand;

    @Before
    public void before(){
        repository=new ItemManagementRepositoryImpl();
        stepsToReproduce=List.of("First step", "Second step", "Third step");
        testTeam=new TeamImpl("TestTeamName");
        repository.addTeam("TestTeamName",testTeam);
        testBoard=new BoardImpl("BoardName");
        repository.getTeam("TestTeamName").addBoard(testBoard);
        testBug=new BugImpl("TestBugTitle", "description test", "HIGH", "MAJOR", stepsToReproduce);
        repository.findBoard("BoardName","TestTeamName").addItemToBoard(testBug);
        testCommand=new FilterByStatus(repository);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments () {
        //Arrange
        List<String> testList = new ArrayList<>();
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Active");
        testList.add("Active");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_FilterByStatus() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Active");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName","TestTeamName").
                getWorkItems().get(0).getItemsStatus(),"Active" );
    }



}
