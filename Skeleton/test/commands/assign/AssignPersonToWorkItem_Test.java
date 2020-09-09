package commands.assign;

import com.wim.commands.add.AddCommentToWorkItem;
import com.wim.commands.assign.AssignPersonToWorkItem;
import com.wim.commands.contracts.Command;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.*;
import com.wim.models.implementation.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class AssignPersonToWorkItem_Test {

    private ItemManagementRepository repository;
    private Command testCommand;
    private Team testTeam;
    private Member testMember;
    private Board testBoard;
    private Bug testBug;
    private Story testStory;
    private List<String> stepsToReproduce = List.of("First step", "Second step", "Third step");


    @Before
    public void before(){
        repository = new ItemManagementRepositoryImpl();
        testTeam = new TeamImpl("TeamNameTest");
        repository.addTeam("TeamNameTest", testTeam);
        testMember = new MemberImpl("Steven");
        repository.addMember("Steven", testMember);
        repository.getTeam("TeamNameTest").addMemberToTeam(testMember);
        testBoard = new BoardImpl("BoardName");
        repository.getTeam("TeamNameTest").addBoard(testBoard);
        testBug = new BugImpl("Test bug title", "description test", "HIGH", "MAJOR", stepsToReproduce);
        repository.findBoard("BoardName","TeamNameTest").addItemToBoard(testBug);
        testStory=new StoryImpl("Test story title","description test","MEDIUM","HIGH");
        repository.findBoard("BoardName","TeamNameTest").addItemToBoard(testStory);
        testCommand = new AssignPersonToWorkItem(repository);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestParameter");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        testList.add("TestParameter");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }


    @Test
    public void execute_should_AssignWorkItemToPerson_when_inputIsValid_forBug(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("TeamNameTest");
        testList.add("Test bug title");
        //Act
        testCommand.execute(testList);
        //Assert
        Assert.assertEquals(repository.getTeam("TeamNameTest").getBoard("BoardName").findBug("Test bug title")
                .getAsignee("Steven"),testMember.getName());

    }

    @Test
    public void execute_should_AssignWorkItemToPerson_when_inputIsValid_forStory(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("TeamNameTest");
        testList.add("Test story title");
        //Act
        testCommand.execute(testList);
        //Assert
        Assert.assertEquals(repository.getTeam("TeamNameTest").getBoard("BoardName").findStory("Test story title")
                .getAsignee("Steven"),testMember.getName());

    }

}
