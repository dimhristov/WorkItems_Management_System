package models;

import com.wim.models.contracts.Member;
import com.wim.models.implementation.CommentImpl;
import com.wim.models.implementation.FeedbackImpl;
import com.wim.models.implementation.MemberImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.wim.models.common.constants.Constants.DESCRIPTION_MAX_NAME_LEN;
import static com.wim.models.common.constants.Constants.TEAM_MAX_NAME_LEN;
import static com.wim.models.common.constants.Constants.DESCRIPTION_MAX_NAME_LEN;


public class CommentImpl_Test {

    @Test
    public void Constructor_ShouldThrow_WhenCommentDescriptionIsBelowMinLength() {
        // Arrange, Act,Assert
        String commentDescription = "shortDesc";
        Member member = new MemberImpl("Steven");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(member, commentDescription));
    }

    @Test
    public void Constructor_ShouldThrow_WhenCommentDescriptionIsAboveMaxLength() {
        // Arrange, Act,Assert
        Member member = new MemberImpl("Steven");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(member,(new String(new char[DESCRIPTION_MAX_NAME_LEN+1] ))));
    }




}
