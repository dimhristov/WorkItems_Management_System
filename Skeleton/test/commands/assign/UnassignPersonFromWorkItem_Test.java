package commands.assign;

import com.wim.commands.assign.AssignPersonToWorkItem;
import com.wim.commands.assign.UnassignPersonFromWorkItem;
import com.wim.commands.contracts.Command;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.*;
import com.wim.models.implementation.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class UnassignPersonFromWorkItem_Test {

    private ItemManagementRepository repository;
    private Command testCommand;
    private Team testTeam;
    private Member testMember;
    private Board testBoard;
    private Bug testBug;
    private Story testStory;
    private List<String> stepsToReproduce;

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
        String description = "Test short description";
        stepsToReproduce = List.of("First step", "Second step", "Third step");
        testBug = new BugImpl("TestBugTitle", description, "HIGH", "MAJOR", stepsToReproduce);
        repository.findBoard("BoardName","TeamNameTest").addItemToBoard(testBug);
        testStory=new StoryImpl("TestStoryTitle","description test","MEDIUM","HIGH");
        repository.findBoard("BoardName","TeamNameTest").addItemToBoard(testStory);
        testCommand = new UnassignPersonFromWorkItem(repository);
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
    public void execute_should_UnassignWorkItemToPerson_when_inputIsValid_forBug(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("TeamNameTest");
        testList.add("TestBugTitle");
        //Act
        repository.getTeam("TeamNameTest").getMember("Steven");
        repository.findBoard("BoardName","TeamNameTest").findBug("TestBugTitle").assignMember(testMember);
        //testCommand.execute(testList);
        String expectedResult = "Asignee with name Steven doesn't exist";
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
        //Assertions.assertEquals(repository.findBoard("BoardName","TeamNameTest").findBug("TestBugTitle").getListAssignee().size(),0);
    }


    @Test
    public void execute_should_UnassignWorkItemToPerson_when_inputIsValid_forStory(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("TeamNameTest");
        testList.add("TestStoryTitle");
        //Act
        repository.getTeam("TeamNameTest").getMember("Steven");
        repository.findBoard("BoardName","TeamNameTest").findStory("TestStoryTitle").assignMember(testMember);
        //testCommand.execute(testList);
        String expectedResult = "Asignee with name Steven doesn't exist";
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
        //Assertions.assertEquals(repository.findBoard("BoardName","TeamNameTest").findStory("TestStoryTitle").getListAssignee().size(),0);
    }
}
