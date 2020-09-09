package commands.list;

import com.wim.commands.contracts.Command;
import com.wim.commands.list.FilterItems;
import com.wim.commands.list.Sort;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.*;
import com.wim.models.implementation.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class Sort_Test {

        private ItemManagementRepository repository;
        private List<String> stepsToReproduce;
        private Team testTeam;
        private Board testBoard;
        private Bug testBug;
        private Story testStory;
        private Feedback testFeedback;
        private Command testCommand;

        @Before
        public void before () {
            repository = new ItemManagementRepositoryImpl();
            stepsToReproduce = List.of("First step", "Second step", "Third step");
            testTeam = new TeamImpl("TestTeamName");
            repository.addTeam("TestTeamName", testTeam);
            testBoard = new BoardImpl("BoardName");
            repository.getTeam("TestTeamName").addBoard(testBoard);
            testBug = new BugImpl("TestBugTitle", "description test", "HIGH", "MAJOR", stepsToReproduce);
            repository.findBoard("BoardName", "TestTeamName").addItemToBoard(testBug);
            String description = "Story description";
            testStory = new StoryImpl("StoryTitle", description, "Large", "Low");
            repository.findBoard("BoardName", "TestTeamName").addItemToBoard(testStory);
            String description1 = "Feedback description";
            testFeedback = new FeedbackImpl("FeedbackTitle", description1, 5);
            repository.findBoard("BoardName", "TestTeamName").addItemToBoard(testFeedback);
            testCommand = new Sort(repository);
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
            testList.add("bug");
            testList.add("bug");
            //Act,Assert
            Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testList));
        }

    @Test
    public void execute_should_SortByTitle() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Title");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName","TestTeamName").
                getWorkItems().get(0).getTitle(),"TestBugTitle" );
    }


    @Test
    public void execute_should_SortByPriority() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Priority");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName","TestTeamName").
                getWorkItems().get(0).getPriorityType().getIndex(),3 );
    }

    @Test
    public void execute_should_SortBySeverity() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Severity");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName","TestTeamName")
                .findBug("TestBugTitle").getSeverity().getIndex(),2);
    }

    @Test
    public void execute_should_SortBySize() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Size");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName","TestTeamName")
                .findStory("StoryTitle").getSize().getIndex(),3);
    }

    @Test
    public void execute_should_SortByRating() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Rating");
        //Act
        testCommand.execute(testList);
        //Assert
        Assertions.assertEquals(repository.findBoard("BoardName","TestTeamName")
                .findFeedback("FeedbackTitle").getRating(),5);
    }
}
