package delete;

import com.wim.commands.contracts.Command;
import com.wim.commands.delete.DeleteBoard;
import com.wim.commands.delete.DeleteMember;
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

public class DeleteMember_Test {
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
        repository.addMember("Steven", member);
        repository.getTeam("Alpha").addMemberToTeam(member);
        command = new DeleteMember(repository);
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
    public void deleteMember_should_deleteMemberFromRepository() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getMembers().size(), 0);
    }

    @Test
    public void deleteMember_should_moveMemberToArchive() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Steven");
        //Act
        command.execute(testList);
        // Assert
        Assertions.assertEquals(repository.getArchive().getMemberArchive().size(), 1);
    }
}
