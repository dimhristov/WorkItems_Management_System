package commands.add;

import com.wim.commands.add.AddCommentToWorkItem;
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

public class AddCommentToWorkItem_Test {

    private ItemManagementRepository testRepository;
    private Command testCommand;
    private Member testMember;
    private Board testBoard;
    private Team testTeam;
    private WorkItem testItem;
    private Comment testComment;
    private List<String> stepsToReproduce = List.of("First step", "Second step", "Third step");


    @Before
    public void before () {
        testRepository = new ItemManagementRepositoryImpl();
        testTeam = new TeamImpl("TeamNameTest");
        testRepository.addTeam("TeamNameTest", testTeam);
        testMember = new MemberImpl("Steven");
        testRepository.addMember("Steven", testMember);
        testRepository.getTeam("TeamNameTest").addMemberToTeam(testMember);
        testBoard = new BoardImpl("BoardName");
        testRepository.getTeam("TeamNameTest").addBoard(testBoard);
        testItem = new BugImpl("TestTitleName", "description test", "HIGH", "MAJOR", stepsToReproduce);
        testRepository.findBoard("BoardName","TeamNameTest").addItemToBoard(testItem);
        testComment = new CommentImpl(testMember, "MessageTest");
        testCommand = new AddCommentToWorkItem(testRepository);

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
    public void execute_should_addCommentToWorkItem_when_inputIsValid () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("TeamNameTest");
        testList.add("TestTitleName");
        testList.add("[MessageTest]");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(testRepository.findBoard("BoardName","TeamNameTest").getItem("TestTitleName").
                getComments().size(),1);
    }

    @Test
    public void execute_should_addToMemberHistory_when_inputIsValid () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("TeamNameTest");
        testList.add("TestTitleName");
        testList.add("[MessageTest]");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(testRepository.getTeam("TeamNameTest").getMember("Steven")
                .getActivityHistory().size(),1);
    }


    @Test
    public void execute_should_addToItemHistory_when_inputIsValid () {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("TeamNameTest");
        testList.add("TestTitleName");
        testList.add("[MessageTest]");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(testRepository.findBoard("BoardName","TeamNameTest").getItem("TestTitleName")
                .getHistory().size(),1);
    }


}


