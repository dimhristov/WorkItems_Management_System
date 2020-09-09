package models;

import com.wim.models.contracts.Board;
import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.WorkItem;
import com.wim.models.implementation.BoardImpl;
import com.wim.models.implementation.BugImpl;
import com.wim.models.implementation.MemberImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.wim.models.common.constants.Constants.*;


public class BoardImpl_Test {

    List<String> stepsToReproduce = List.of("First step", "Second step" , "Third step");

    @Test
    public void BoardImpl_ShouldImplementBoardInterface(){
        // Arrange, Act,Assert
        BoardImpl board= new BoardImpl("Board1");
        Assertions.assertTrue(board instanceof Board);
    }

    @Test
    public void Constructor_ShouldThrow_WhenBoardNameIsNull(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(null));
    }

    @Test
    public void Constructor_ShouldThrow_WhenBoardNameIsBelowMinLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(new String(new char[BOARD_MIN_NAME_LEN-1] )));
    }


    @Test
    public void Constructor_ShouldThrow_WhenBoardNameIsAboveMaxLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(new String(new char[BOARD_MAX_NAME_LEN+1] )));
    }

    @Test
    public void Check_addItemToBoard(){
        // Arrange
        Board board=new BoardImpl("Board1");
        Bug bug= new BugImpl("BugProblem","description test",
                "HIGH", "MAJOR",stepsToReproduce);
        // Act
        board.addItemToBoard(bug);
        // Assert
        Assertions.assertEquals(bug,board.getWorkItems().get(0));
    }

    @Test
    public void Check_removeItemFromBoard(){
        // Arrange
        Board board=new BoardImpl("Board1");
        Bug bug= new BugImpl("BugProblem","description test",
                "HIGH", "MAJOR",stepsToReproduce);
        // Act
        board.addItemToBoard(bug);
        board.removeItemFromBoard(bug);
        // Assert
        Assertions.assertTrue(board.getWorkItems().isEmpty());
    }


    public void Check_addToHistory(){
        // Arrange
        Board board=new BoardImpl("Board1");
        String testHistory="Some text";
        // Act
        board.addToHistory(testHistory);
        // Assert
        Assertions.assertEquals(testHistory,board.getActivityHistory().get(0));
    }
}
