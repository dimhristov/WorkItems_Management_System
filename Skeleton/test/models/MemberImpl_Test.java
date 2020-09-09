package models;


import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.contracts.WorkItem;
import com.wim.models.implementation.BugImpl;
import com.wim.models.implementation.MemberImpl;
import com.wim.models.implementation.TeamImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;

import static com.wim.models.common.constants.Constants.*;


public class MemberImpl_Test {

    @Test
    public void MemberImpl_ShouldImplementMemberInterface(){
        // Arrange, Act,Assert
        MemberImpl member= new MemberImpl("Georgi");
        Assertions.assertTrue(member instanceof Member);
    }

    @Test
    public void Constructor_ShouldThrow_WhenMemberNameIsNull(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new MemberImpl(null));
    }

    @Test
    public void Constructor_ShouldThrow_WhenMemberNameIsBelowMinLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new MemberImpl(new String(new char[MEMBER_MIN_NAME_LEN-1] )));
    }


    @Test
    public void Constructor_ShouldThrow_WhenMemberNameIsAboveMaxLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new MemberImpl(new String(new char[MEMBER_MAX_NAME_LEN+1] )));
    }


    List<String> stepsToReproduce = List.of("First step", "Second step" , "Third step");
    @Test
    public void AddWorkItem_Should_AddActivityToMember(){
        // Arrange
        Member member = new MemberImpl("Steven");
        Bug bug= new BugImpl("BugProblem","description test",
                "HIGH", "MAJOR",stepsToReproduce);
        // Act
        member.addWorkItem(bug);
        // Assert
        Assertions.assertEquals(bug,member.getWorkItems().get(0));
    }


    @Test
    public void AddToHistory_Should_AddActivityToHistory(){
        // Arrange
        Member member = new MemberImpl("Steven");
        String testHistory="Some text";
        // Act
        member.addToHistory(testHistory);
        // Assert
        Assertions.assertEquals(testHistory,member.getActivityHistory().get(0));
    }
}
