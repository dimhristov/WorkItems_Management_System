package commands.show;

import com.wim.commands.contracts.Command;
import com.wim.commands.show.ShowAllTeamMembers;
import com.wim.commands.show.ShowAllTeams;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.MemberImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class ShowAllTeams_Test {

    private ItemManagementRepository repository;
    private Team testTeam;
    private Command testCommand;


    @Before
    public void before () {
        this.repository = new ItemManagementRepositoryImpl();
        this.testTeam = new TeamImpl("TestTeamName");
        repository.addTeam("TestTeamName", testTeam);
        this.testCommand = new ShowAllTeams(repository);
    }

    @Test
    public void execute_should_ShowAllTeams_when_inputIsValid () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestTeamName");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.getTeams().keySet().toString().
                replaceAll("[\\[\\]]", ""), "TestTeamName");

    }


}
