package commands.show;

import com.wim.commands.contracts.Command;
import com.wim.commands.show.ShowAllMembers;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;
import com.wim.models.implementation.MemberImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ShowAllMembers_Test {

    private ItemManagementRepository repository;
    private Member testMember;
    private Command testCommand;

    @Before
    public void before(){
        this.repository=new ItemManagementRepositoryImpl();
        this.testMember=new MemberImpl("Steven");
        repository.addMember("Steven",testMember);
        this.testCommand=new ShowAllMembers(repository);
    }

    @Test
    public void execute_should_ShowAllMember(){
        //Arrange
        List<String> testList = new ArrayList<>();
        repository=new ItemManagementRepositoryImpl();
        testMember=new MemberImpl("Steven");
        repository.addMember("Steven",testMember);
        testCommand=new ShowAllMembers(repository);

        String expectedResult = "◀◀◀ Member: Steven";

        //ActAssert
        Assert.assertEquals(testCommand.execute(testList).substring(0, 18), expectedResult);

    }
}

