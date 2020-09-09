package models;


import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Member;
import com.wim.models.implementation.BugImpl;
import com.wim.models.implementation.MemberImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static com.wim.models.common.constants.Constants.*;


public class BugImpl_Test {


    List<String> stepsToReproduce = List.of("First step", "Second step" , "Third step");
    @Test
    public void BugImpl_ShouldImplementBugInterface(){
        // Arrange, Act,Assert
        BugImpl bug= new BugImpl("BugProblem","description test",
                              "HIGH", "MAJOR",stepsToReproduce);
        Assertions.assertTrue(bug instanceof Bug);
    }

    @Test
    public void Constructor_ShouldThrow_WhenBugTitleIsNull(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(null,"description test", "HIGH", "MAJOR",stepsToReproduce));
    }

    @Test
    public void Constructor_ShouldThrow_WhenBugTitleIsBelowMinLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(new String(new char[TITLE_MIN_NAME_LEN-1]),"description test",
                            "HIGH", "MAJOR",stepsToReproduce));
    }


    @Test
    public void Constructor_ShouldThrow_WhenBugTitleIsIsIsAboveMaxLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(new String(new char[TITLE_MAX_NAME_LEN+1]),"description test",
                                    "HIGH", "MAJOR",stepsToReproduce));
    }

    @Test
    public void Constructor_ShouldThrow_WhenDescriptionIsBelowMinLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl("BugProblem",new String(new char[DESCRIPTION_MIN_NAME_LEN-1]),
                        "HIGH", "MAJOR",stepsToReproduce));
    }

    @Test
    public void Constructor_ShouldThrow_WhenDescriptionIsAboveMaxLength(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl("BugProblem",new String(new char[DESCRIPTION_MAX_NAME_LEN+1]),
                        "HIGH", "MAJOR",stepsToReproduce));
    }

    @Test
    public void Constructor_ShouldThrow_WhenDescriptionIsNull(){
        // Arrange, Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl("BugProblem",new String(new char[DESCRIPTION_MAX_NAME_LEN+1]),
                        "HIGH", "MAJOR",null));
    }

    @Test
    public void AssignMember_ShouldThrow_WhenAssignMember(){
        // Arrange
        Member member = new MemberImpl("Steven");
        Bug bug= new BugImpl("BugProblem","description test",
                "HIGH", "MAJOR",stepsToReproduce);
        //Act
        bug.assignMember(member);
        //Assert
        Assertions.assertEquals(member.getName(),bug.getAsignee(member.getName()));
    }

       //TODO
    @Test
    public void unassignMember_ShouldThrow_WhenUnassignMember(){
        // Arrange
        Member member = new MemberImpl("Steven");
        Bug bug= new BugImpl("BugProblem","description test",
                "HIGH", "MAJOR",stepsToReproduce);
        //Act
        bug.assignMember(member);
        //Assert
        Assertions.assertEquals(member.getName(),bug.getAsignee(member.getName()));
    }

}
