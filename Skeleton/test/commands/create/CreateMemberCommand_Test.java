package commands.create;

import com.wim.commands.contracts.Command;
import com.wim.commands.creation.CreateMemberCommand;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.core.factories.ItemManagementFactoryImpl;
import com.wim.models.contracts.Member;
import com.wim.models.implementation.MemberImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class CreateMemberCommand_Test {
    private Command testCommand;
    private ItemManagementRepository testRepository;
    private ItemManagementFactory testFactory;

    @Before
    public void before() {
        testRepository = new ItemManagementRepositoryImpl();
        testFactory = new ItemManagementFactoryImpl();
        testCommand = new CreateMemberCommand(testFactory, testRepository);
    }

    @Test
    public void execute_shouldThrow_IllegalArgumentException_when_passedLessArguments() {
        //Arrange
        List<String> testParameters = new ArrayList<>();
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_shouldThrow_IllegalArgumentException_when_passedMoreArguments() {
        //Arrange
        List<String> testParameters = new ArrayList<>();
        testParameters.add("test");
        testParameters.add("test");
        testParameters.add("test");
        //Act,Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testParameters));
    }


    @Test
    public void execute_should_createNewMember_when_inputIsValid() {
        //Arrange
        List<String> testParameters = new ArrayList<>();
        testParameters.add("Steven");
        //Act
        testCommand.execute(testParameters);
        Member testMember = testRepository.getMember("Steven");
        // Assert
        Assertions.assertEquals(testMember.getName(), "Steven");
    }

    @Test
    public void execute_should_throwException_when_person_exists() {
        // Arrange
        Member member = new MemberImpl("Steven");
        testRepository.addMember(member.getName(), member);
        List<String> testParameters = new ArrayList<>();
        testParameters.add("Steven");
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> testCommand.execute(testParameters));
    }

}
