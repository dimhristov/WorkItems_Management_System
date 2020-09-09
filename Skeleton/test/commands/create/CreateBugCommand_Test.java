package commands.create;

import com.wim.commands.contracts.Command;
import com.wim.commands.creation.CreateBugCommand;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.core.factories.ItemManagementFactoryImpl;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class CreateBugCommand_Test {
    private ItemManagementRepository repository;
    private ItemManagementFactory factory;
    private Command command;
    private Team team;
    private Board board;

    @Before
    public void before() {
        factory = new ItemManagementFactoryImpl();
        repository = new ItemManagementRepositoryImpl();
        command = new CreateBugCommand(factory, repository);
        team = new TeamImpl("Alpha");
        repository.addTeam("Alpha", team);
        board = new BoardImpl("BoardName");
        repository.getTeam("Alpha").getBoardList().add(board);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        testList.add("BoardName");
        testList.add("BoardName");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }
    @Test
    public void execute_should_createBug_when_boardExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BugTitleName");
        testList.add("[Description]");
        testList.add("Low");
        testList.add("Major");
        testList.add("Alpha");
        testList.add("BoardName");
        testList.add("{step1|step2}");

        //Act
        command.execute(testList);
        //Assert
        Assertions.assertEquals(repository.getTeam("Alpha").getBoardList().size(),1);
    }
}
