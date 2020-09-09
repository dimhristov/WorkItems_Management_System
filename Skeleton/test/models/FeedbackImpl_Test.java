package models;


import com.wim.models.contracts.Feedback;
import com.wim.models.contracts.Member;
import com.wim.models.implementation.FeedbackImpl;
import com.wim.models.implementation.MemberImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.wim.models.common.constants.Constants.DESCRIPTION_MAX_NAME_LEN;
import static com.wim.models.common.constants.Constants.TEAM_MAX_NAME_LEN;

public class FeedbackImpl_Test {

    @Test
    public void Constructor_ShouldThrow_WhenFeedbackdTitleIsBelowMinLength() {
        // Arrange, Act,Assert
        String title = "Feed";
        String description = "This is bug description";
        int rating = 5;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedbackImpl(title, description, rating));
    }

    @Test
    public void Constructor_ShouldThrow_WhenFeedbackdTitleIsAboveMaxLength() {
        // Arrange, Act,Assert
        String title = "FeedbackNameIsTooLongSoItMustBeShorter_NotMoreThen_50_symbols";
        String description = "This is bug description";
        int rating = 5;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedbackImpl(title, description, rating));
    }

    @Test
    public void Constructor_ShouldThrow_WhenFeedbackdDescriptionIsAboveMaxLength() {
        // Arrange, Act,Assert
        String title = "Title";
        int rating = 5;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedbackImpl(title, (new String(new char[DESCRIPTION_MAX_NAME_LEN + 1])), rating));
    }

    @Test
    public void Constructor_ShouldThrow_WhenFeedbackdDescriptionIsBellowMinLength() {
        // Arrange, Act,Assert
        String title = "Title";
        String description = "Short description";
        int rating = 5;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedbackImpl(title, description, rating));
    }

    @Test
    public void removeAsignee_ShouldThrow_IllegalArgumentException() {
        // Arrange, Act,Assert
        Member member = new MemberImpl("Steven");
        String title = "FeedbackTitle";
        String description = "Short description";
        int rating = 5;
        Feedback feedback = new FeedbackImpl(title, description, rating);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> feedback.removeAsignee(member));
    }

    @Test
    public void changeStatusFeedback_Should_Change_StatusOfFeedback() {
        // Arrange
        String title = "FeedbackTitle";
        String description = "Short description here";
        int rating = 5;
        // Act
        Feedback feedback = new FeedbackImpl(title, description, rating);
        feedback.changeStatusFeedback("DONE");
        String expectedResult = feedback.getItemsStatus();
        //Assert
        Assertions.assertEquals(expectedResult, "Done");
    }

    @Test
    public void changeRatingFeedback_Should_Change_RatingOfFeedback() {
        // Arrange
        String title = "FeedbackTitle";
        String description = "Short description here";
        int rating = 5;
        // Act
        Feedback feedback = new FeedbackImpl(title, description, rating);
        feedback.changeRatingFeedback(12);
        int expectedResult = feedback.getRating();
        //Assert
        Assertions.assertEquals(expectedResult, 12);
    }


}
