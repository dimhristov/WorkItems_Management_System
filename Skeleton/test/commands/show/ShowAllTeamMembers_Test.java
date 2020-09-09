package commands.show;

import com.wim.commands.contracts.Command;
import com.wim.commands.show.ShowAllTeamMembers;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementFactory;
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

public class ShowAllTeamMembers_Test {

    private ItemManagementRepository repository;
    private Team testTeam;
    private Member testMember;
    private List<Member> testShow;
    private Command testCommand;

    @Before
    public void before () {
        this.repository = new ItemManagementRepositoryImpl();
        this.testTeam = new TeamImpl("TestTeamName");
        repository.addTeam("TestTeamName", testTeam);
        this.testMember = new MemberImpl("Steven");
        repository.addMember("Steven", testMember);
        repository.getTeam("TestTeamName").addMemberToTeam(testMember);
        this. testShow = testTeam.getMemberList();
        this.testCommand = new ShowAllTeamMembers(repository);

    }


    @Test
    public void execute_should_throwException_when_passedLessArguments () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestTeamName");
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
    public void execute_should_showAllTeamMember(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestTeamName");
        //Act
        testCommand.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getTeam("TestTeamName").getMemberList().get(0).getName(),"Steven");
    }

}
