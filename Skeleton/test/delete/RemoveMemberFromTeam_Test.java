package delete;

import com.wim.commands.contracts.Command;
import com.wim.commands.delete.DeleteMember;
import com.wim.commands.delete.RemoveMemberFromTeam;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.contracts.WorkItem;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.MemberImpl;
import com.wim.models.implementation.StoryImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class RemoveMemberFromTeam_Test {
    private ItemManagementRepository repository;
    private Command command;
    private Team team;
    private Board board;
    private Member member;
    private WorkItem item;

    @Before
    public void before() {
        repository = new ItemManagementRepositoryImpl();
        team = new TeamImpl("Alpha");
        repository.addTeam("Alpha", team);
        board = new BoardImpl("BoardName");
        repository.getTeam("Alpha").getBoardList().add(board);
        member = new MemberImpl("Steven");
        repository.addMember("Steven", member);
        repository.getTeam("Alpha").addMemberToTeam(member);
        String description = "Description story";
        item = new StoryImpl("StoryTitle", description, "Large", "Low");
        repository.findBoard(board.getName(), team.getName()).addItemToBoard(item);
        command = new RemoveMemberFromTeam(repository);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        //Arrange
        List<String> testList = new ArrayList<>();
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
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }

    @Test
    public void removeMemberFromTeam_should_removeTheMemberFromTheGivenTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("Alpha");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getTeam(testList.get(1)).getMemberList().size(), 0);
    }

    @Test
    public void removeMemberFromTeam_should_moveTheMemberToTheArchive() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("Alpha");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getArchive().getMemberArchive().size(), 1);
    }

    @Test
    public void removeMemberFromTeam_should_removeMemberFromAsignedTasks() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("Alpha");

        //Act
        command.execute(testList);
        repository.findBoard("BoardName","Alpha")
                  .findStory("StoryTitle").assignMember(member);
        // Assert
        Assertions.assertEquals(repository.findBoard("BoardName","Alpha")
                                          .findStory("StoryTitle").getAsignee("Steven"), "Steven");
    }

    @Test
    public void removeMemberFromTeam_should_returnValidMessage_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        testList.add("Alpha");
        String expectedMessage = "Member with name Steven has been removed from team Alpha";
        //Act Assert
        Assertions.assertEquals(expectedMessage,command.execute(testList));
    }
}
