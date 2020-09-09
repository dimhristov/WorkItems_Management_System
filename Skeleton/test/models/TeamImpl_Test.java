package models;

import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.MemberImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.wim.models.common.constants.Constants.*;

public class TeamImpl_Test {

    @Test
    public void TeamImpl_ShouldImplementTeamInterface(){
        // Arrange, Act,Assert
        TeamImpl team= new TeamImpl("Alpha");
        Assertions.assertTrue(team instanceof Team);
    }

    @Test
    public void Constructor_ShouldThrow_WhenTeamNameIsNull(){
    // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TeamImpl(null));
    }

    @Test
    public void Constructor_ShouldThrow_WhenTeamNameIsBelowMinLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new TeamImpl(new String(new char[TEAM_MIN_NAME_LEN-1] )));
    }

    @Test
    public void Constructor_ShouldThrow_WhenTeamNameIsAboveMaxLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new TeamImpl(new String(new char[TEAM_MAX_NAME_LEN+1] )));
    }

    @Test
    public void AddMemberToTeam_Should_AddMemberToTeam(){
    // Arrange
        Team team= new TeamImpl("Alpha");
        Member member = new MemberImpl("Steven");
    // Act
        team.addMemberToTeam(member);
    // Assert
        Assertions.assertEquals(member,team.getMemberList().get(0));
    }

    @Test
    public void AddMemberToTeam_Should_RemoveMemberFromTeam(){
        // Arrange
        Team team= new TeamImpl("Alpha");
        Member member = new MemberImpl("Steven");
        // Act
        team.addMemberToTeam(member);
        team.removeMemberFromTeam(member);
        // Assert
        Assertions.assertTrue(team.getMemberList().isEmpty());
    }

    @Test
    public void AddBoardToTeam_Should_AddBoard(){
        // Arrange
        Team team= new TeamImpl("Alpha");
        Board board = new BoardImpl("Board1");
        // Act
        team.addBoard(board);
        // Assert
        Assertions.assertEquals(board,team.getBoardList().get(0));
    }


    @Test
    public void RemoveBoardFromTeam_Should_RemoveBoard(){
        // Arrange
        Team team= new TeamImpl("Alpha");
        Board board = new BoardImpl("Board1");
        // Act
        team.addBoard(board);
        team.removeBoard(board);
        // Assert
        Assertions.assertTrue(team.getBoardList().isEmpty());
    }


}
