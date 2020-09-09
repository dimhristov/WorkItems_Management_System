package com.wim.models.implementation;


import com.wim.models.common.enums.FeedbackStatusType;
import com.wim.models.common.enums.PriorityType;
import com.wim.models.contracts.Feedback;
import com.wim.models.contracts.Member;

import java.util.Arrays;

import static com.wim.ValidationHelper.isNegative;
import static com.wim.models.common.constants.Constants.RATING_ERR_MESS;

public class FeedbackImpl extends WorkItemImpl implements Feedback {

    private static final String FEEDBACK_ASSIGNEE_ERR_MESS = "Feedback can 't have assignees";
    private int rating;
    private FeedbackStatusType feedbackType;

    public FeedbackImpl(String title, String description, int rating) {
        super(title, description);
        setRating(rating);
        this.feedbackType = FeedbackStatusType.NEW;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getAsignee(String name) {
        return "Feedback can't have assignees";
    }

    public void assignMember(Member member) {
        throw new IllegalArgumentException(FEEDBACK_ASSIGNEE_ERR_MESS);
    }

    @Override
    public void removeAsignee(Member member) {
        throw new IllegalArgumentException(FEEDBACK_ASSIGNEE_ERR_MESS);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    public int getRating() {
        return rating;
    }

    @Override
    public FeedbackStatusType getFeedbackType() {
        return feedbackType;
    }

    @Override
    public void changeStatusFeedback(String feedbackType) {
        this.feedbackType = FeedbackStatusType.valueOf(feedbackType);
    }

    @Override
    public String getItemsStatus() {
        return this.feedbackType.toString();
    }

    @Override
    public PriorityType getPriorityType() {
        throw new IllegalArgumentException();
    }

    private void setRating(int rating) {
        isNegative(rating, RATING_ERR_MESS);
        this.rating = rating;
    }

    @Override
    public void changeRatingFeedback(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------Feedback data ----------");
        sb.append(System.lineSeparator());
        sb.append(super.toString());
        sb.append(String.format("Status: %s%n" +
                "Rating: %s%n", getFeedbackType(), getRating()));
        return sb.toString();
    }
}
