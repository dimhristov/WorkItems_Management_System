package commands.list;

import com.wim.commands.contracts.Command;
import com.wim.commands.list.FilterByAsignee;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.*;
import com.wim.models.implementation.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class FilterByAsignee_Test {



    private ItemManagementRepository repository;
    private List<String> stepsToReproduce;
    private Member testAsignee;
    private Team testTeam;
    private Board testBoard;
    private Bug testBug;
    private Story testStory;
    private Command testCommand;


    @Before
    public void before(){
        repository=new ItemManagementRepositoryImpl();
        stepsToReproduce=List.of("First step", "Second step", "Third step");
        testAsignee=new MemberImpl("Steven");
        repository.addMember("Steven",testAsignee);
        testTeam=new TeamImpl("TestTeamName");
        repository.addTeam("TestTeamName",testTeam);
        repository.getTeam("TestTeamName").addMemberToTeam(testAsignee);
        testBoard=new BoardImpl("BoardName");
        repository.getTeam("TestTeamName").addBoard(testBoard);
        testBug=new BugImpl("TestBugTitle", "description test", "HIGH", "MAJOR", stepsToReproduce);
        repository.findBoard("BoardName","TestTeamName").addItemToBoard(testBug);
           testCommand=new FilterByAsignee(repository);
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
        testList.add("Steven");
        testList.add("Steven");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
    }

    @Test
    public void execute_should_FilterByAssignee() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertNotEquals(repository.findBoard("BoardName","TestTeamName").
                getWorkItems().get(0).getTitle(),"Steven" );
    }


}

