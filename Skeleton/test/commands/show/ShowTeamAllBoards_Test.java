package commands.show;

import com.wim.commands.contracts.Command;
import com.wim.commands.show.ShowTeamAllBoards;
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

public class ShowTeamAllBoards_Test {

    private ItemManagementRepository repository;
    private Board testBoard;
    private Team testTeam;
    private Command testCommand;


    @Before
    public void before () {
        repository = new ItemManagementRepositoryImpl();
        testTeam = new TeamImpl("TestTeamName");
        repository.addTeam("TestTeamName", testTeam);
        testBoard =new BoardImpl("BoardName");
        repository.getTeam("TestTeamName").addBoard(testBoard);
        testCommand = new ShowTeamAllBoards(repository);
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
        testList.add("TestTeamName");
        testList.add("TestTeamName");

        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_ShowTeamBoards () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestTeamName");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.getTeam("TestTeamName")
                .getBoardList().get(0).getName(), "BoardName");
    }

}
