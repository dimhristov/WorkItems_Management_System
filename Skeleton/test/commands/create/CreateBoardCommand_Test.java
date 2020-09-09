package commands.create;

import com.wim.commands.contracts.Command;
import com.wim.commands.creation.CreateBoardCommand;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.core.factories.ItemManagementFactoryImpl;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class CreateBoardCommand_Test {

    private ItemManagementRepository repository;
    private ItemManagementFactory factory;
    private Command command;
    private Team team;

    @Before
    public void before() {
        factory = new ItemManagementFactoryImpl();
        repository = new ItemManagementRepositoryImpl();
        command = new CreateBoardCommand(factory, repository);
        team = new TeamImpl("Alpha");
        repository.addTeam("Alpha", team);
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
    public void execute_should_createBoard_when_teamExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        testList.add("Alpha");
        //Act
        command.execute(testList);
        //Assert
        Assertions.assertEquals(repository.getTeam("Alpha").getBoardList().size(),1);
    }

    @Test
    public void execute_shouldThrow_IllegalArgumentException_when_teamDoesNotExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        testList.add("Gamma");
        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }

    @Test
    public void parseParameters_shouldThrow_IllegalArgumentException_when_lessParametersAreGiven() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }

}
