package commands.show;

import com.wim.commands.contracts.Command;
import com.wim.commands.show.ShowBoardActivity;
import com.wim.commands.show.ShowMemberActivity;
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

public class ShowMemberActivity_Test {

    private ItemManagementRepository repository;
    private Member testMember;
    private Command testCommand;


    @Before
    public void before () {
        repository = new ItemManagementRepositoryImpl();
        testMember = new MemberImpl("TestMemberName");
        repository.addMember("TestMemberName", testMember);
        repository.getMember("TestMemberName").addToHistory("ActivityHistory");
        testCommand = new ShowMemberActivity(repository);
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
        testList.add("TestMemberName");
        testList.add("TestMemberName");
        testList.add("TestMemberName");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_ShowMemberActivity () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestMemberName");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.getMember("TestMemberName").
                getActivityHistory().toString().replaceAll("[\\[\\]]", ""), "ActivityHistory");
    }
}
