package commands.change;

import com.wim.commands.change.ChangeRatingFeedback;
import com.wim.commands.change.ChangeSizeStory;
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

public class ChangeSizeStory_Test {
    private ItemManagementRepository repository;
    private Command command;
    private Team team;
    private Board board;
    private Member member;

    @Before
    public void before() {
        repository = new ItemManagementRepositoryImpl();
        team = new TeamImpl("Alpha");
        repository.addTeam("Alpha", team);
        board = new BoardImpl("BoardName");
        repository.getTeam("Alpha").getBoardList().add(board);
        String description = "Story description";
        Story testStory = new StoryImpl("StoryTitle", description, "Large", "Low");
        repository.findBoard(board.getName(), team.getName()).getWorkItems().add(testStory);
        member = new MemberImpl("Steven");
        repository.getTeam("Alpha").addMemberToTeam(member);
        command = new ChangeSizeStory(repository);
        repository.getMembers().put("Steven", member);
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
    public void changeSizeStory_should_changeTheSizeOfStory() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("BoardName");
        testList.add("Alpha");
        testList.add("StoryTitle");
        testList.add("Small");

        //Act,
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.findBoard("BoardName", "Alpha").findStory("StoryTitle").getSize().toString(), "Small");
    }
}
