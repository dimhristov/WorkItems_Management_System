package com.wim.models.contracts;

import com.wim.models.common.enums.FeedbackStatusType;

public interface Feedback extends WorkItem {

    int getRating ();

    FeedbackStatusType getFeedbackType ();

    void changeStatusFeedback (String feedbackType);

    void changeRatingFeedback (int rating);

    void removeAsignee(Member member);
}
