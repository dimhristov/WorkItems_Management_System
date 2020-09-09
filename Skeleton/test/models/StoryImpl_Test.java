package models;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Story;
import com.wim.models.implementation.MemberImpl;
import com.wim.models.implementation.StoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class StoryImpl_Test {

    @Test
    public void getSize_should_return_size() {
        //Arrange
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        String size = story.getSize().toString();
        //Assert
        Assert.assertEquals("Large", size);
    }


    @Test
    public void getStatus_should_return_status() {
        //Arrange
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        String status = story.getStatusType().toString();
        //Assert
        Assert.assertEquals("NotDone", status);
    }

    @Test
    public void getTitle_should_return_title() {
        //Arrange
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        String title = story.getTitle();
        //Assert
        Assert.assertEquals("StoryTitle", title);
    }

    @Test
    public void getAsignee_should_return_asignee() {
        //Arrange
        Member member = new MemberImpl("Steven");
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        story.assignMember(member);
        String returnMemberName = story.getAsignee(member.getName());
        //Assert
        Assert.assertEquals("Steven", returnMemberName);
    }

    @Test
    public void getDescription_should_return_description() {
        //Arrange
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        String description = story.getDescription();
        //Assert
        Assert.assertEquals("Lorem ipsum dolor sit a met", description);
    }

    @Test
    public void unassignMember_should_remove_memberFrom_assignedMembers() {
        //Arrange
        Member member = new MemberImpl("Steven");
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        story.assignMember(member);
        story.unassignMember(member);
        //Assert
        Assert.assertTrue(story.getListAssignee().isEmpty());
    }

    @Test
    public void assignMember_should_add_member_to_assignedMembers() {
        //Arrange
        Member member = new MemberImpl("Steven");
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        story.assignMember(member);
        //Assert
        Assert.assertTrue(!story.getListAssignee().isEmpty());
    }

    @Test
    public void changePriority_should_change_story_priority() {
        //Arrange
        Story story = new StoryImpl("StoryTitle", "Lorem ipsum dolor sit a met", "Large", "Low");
        // Act
        story.changePriority("high");
        //Assert
        Assert.assertTrue(story.getPriorityType().toString().equalsIgnoreCase("High"));
    }


}
