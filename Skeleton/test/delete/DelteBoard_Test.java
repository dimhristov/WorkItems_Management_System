package delete;

import com.wim.commands.change.ChangeRatingFeedback;
import com.wim.commands.contracts.Command;
import com.wim.commands.delete.DeleteBoard;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.MemberImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class DelteBoard_Test {
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
        member = new MemberImpl("Steven");
        repository.getTeam("Alpha").addMemberToTeam(member);
        command = new DeleteBoard(repository);
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
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(testList));
    }

    @Test
    public void deleteBoard_should_deleteBoard() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        testList.add("Alpha");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getTeam("Alpha").getBoardList().size(), 0);
    }

    @Test
    public void deleteBoard_should_moveBoardToArchive() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("BoardName");
        testList.add("Alpha");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getArchive().getBoardArchive().size(), 1);
    }
}
