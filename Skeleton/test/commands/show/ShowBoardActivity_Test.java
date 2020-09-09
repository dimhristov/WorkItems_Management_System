package commands.show;

import com.wim.commands.contracts.Command;
import com.wim.commands.show.ShowBoardActivity;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;


public class ShowBoardActivity_Test {

    private ItemManagementRepository repository;
    private Team testTeam;
    private Board testBoard;
    private Command testCommand;


    @Before
    public void before () {
        repository = new ItemManagementRepositoryImpl();
        testTeam = new TeamImpl("TestTeamName");
        repository.addTeam("TestTeamName", testTeam);
        testBoard = new BoardImpl("BoardName");
        repository.getTeam("TestTeamName").addBoard(testBoard);
        repository.findBoard("BoardName", "TestTeamName").addToHistory("SomeActivity");
        testCommand = new ShowBoardActivity(repository);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestTeamName");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestTeamName");
        testList.add("TestTeamName");
        testList.add("TestTeamName");
        testList.add("TestTeamName");
        testList.add("TestTeamName");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_ShowBoardsActivity () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        testList.add("TestTeamName");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName", "TestTeamName").
                getActivityHistory().toString().replaceAll("[\\[\\]]", ""), "SomeActivity");
    }

}
