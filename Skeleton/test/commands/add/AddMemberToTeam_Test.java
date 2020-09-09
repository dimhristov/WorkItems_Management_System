package commands.add;

import com.wim.commands.add.AddMemberToTeam;
import com.wim.commands.contracts.Command;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.MemberImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class AddMemberToTeam_Test {
    private ItemManagementRepository repository;
    private Command command;
    private Team team;
    private Member member;

    @Before
    public void before() {
        repository = new ItemManagementRepositoryImpl();
        team = new TeamImpl("Alpha");
        repository.addTeam("Alpha", team);
        member = new MemberImpl("Steven");
        repository.addMember("Steven",member);
        command = new AddMemberToTeam(repository);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestParameter");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }

    @Test
    public void addMemberToTeam_should_AddMemberToTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("Alpha");

        //Act,
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getTeam("Alpha").getMemberList().get(0).getName(), "Steven");
    }

}
