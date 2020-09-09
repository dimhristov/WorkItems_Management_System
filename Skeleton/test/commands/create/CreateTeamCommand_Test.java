package commands.create;

import com.wim.commands.contracts.Command;
import com.wim.commands.creation.CreateTeamCommand;
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

public class CreateTeamCommand_Test {
    private ItemManagementRepository repository;
    private ItemManagementFactory factory;
    private Command command;
    private Team team;

    @Before
    public void before() {
        factory = new ItemManagementFactoryImpl();
        repository = new ItemManagementRepositoryImpl();
        command = new CreateTeamCommand(factory, repository);
        team = new TeamImpl("Alpha");
        repository.addTeam("Alpha", team);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        //Arrange
        List<String> testList = new ArrayList<>();
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
    public void execute_should_createTeam_when_validParameters() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Betta");
        //Act
        command.execute(testList);
        //Assert
        Assertions.assertEquals(repository.getTeams().size(),2);
    }

    @Test
    public void execute_shouldThrow_exception_when_teamAlreadyExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Alpha");
        //Act Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }


}
