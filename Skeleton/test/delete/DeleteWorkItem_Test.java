package delete;

import com.wim.commands.contracts.Command;
import com.wim.commands.delete.DeleteTeam;
import com.wim.commands.delete.DeleteWorkItem;
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

public class DeleteWorkItem_Test {
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
        String description = "Story description";
        item = new StoryImpl("StoryTitle", description, "Large", "Low");
        repository.findBoard(board.getName(), team.getName()).addItemToBoard(item);
        command = new DeleteWorkItem(repository);
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
    public void deleteWorkItem_should_deleteWorkItemFromRepository() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("StoryTitle");
        testList.add("BoardName");
        testList.add("Alpha");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.findBoard(testList.get(1),testList.get(2)).getWorkItems().size(), 0);
    }

    @Test
    public void deleteWorkItem_should_moveItemToArchive() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("StoryTitle");
        testList.add("BoardName");
        testList.add("Alpha");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getArchive().getWorkItemsArchive().size(), 1);
    }
}
