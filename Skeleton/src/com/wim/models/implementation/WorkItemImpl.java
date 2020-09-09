package com.wim.models.implementation;


import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.WorkItemsStatusTypes;
import com.wim.models.contracts.Comment;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.WorkItem;


import java.util.ArrayList;
import java.util.List;

import static com.wim.ValidationHelper.*;
import static com.wim.models.common.constants.Constants.*;

public abstract class WorkItemImpl implements WorkItem {

    public static long counter;

    private long id;
    private String title;
    private String description;
    private List<Comment> comments;
    private List<String> history;

    public WorkItemImpl(String title, String description) {
        setTitle(title);
        setDescription(description);
        this.comments = new ArrayList<>();
        this.history = new ArrayList<>();
        id = ++counter;
    }

    @Override
    public abstract String getItemsStatus();

    public long getId() {
        return id;
    }

    public abstract PriorityType getPriorityType();

    public String getTitle() {
        return this.title;
    }

    @Override
    public abstract String getAsignee(String name);

    @Override
    public List<Comment> getComments() {
        isListEmpty(this.comments, EMPTY_LIST_ERR_MESS, comments.getClass().getSimpleName());
        return this.comments;
    }

    @Override
    public abstract void removeAsignee(Member member);


    @Override
    public String printComments() {
        StringBuilder sb = new StringBuilder();
        if (comments.isEmpty()) {
            sb.append("There are no comments for this work item.");
            sb.append(System.lineSeparator());
        } else {
            comments.forEach(c -> {
                sb.append(c.toString());
                sb.append(System.lineSeparator());
            });
        }
        return sb.toString();
    }

    @Override
    public String printHistory() {
        StringBuilder sb = new StringBuilder();
        if (history.isEmpty()) {
            sb.append("Item history is empty");
        } else {
            history.forEach(h -> {
                sb.append(System.lineSeparator());
                sb.append(h);
            });
        }
        return sb.toString();
    }


    public String getDescription() {
        return this.description;
    }

    public List<String> getHistory() {
        isListEmpty(this.history, EMPTY_LIST_ERR_MESS, history.getClass().getSimpleName());
        return this.history;
    }

    @Override
    public void addToHistory(String message) {
        history.add(message);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    private void setTitle(String title) {
        validateArgumentIsNotNull(title, TITLE_NULL_ERR_MESS);
        validateIntRange(title.length(), TITLE_MIN_NAME_LEN, TITLE_MAX_NAME_LEN, TITLE_ERR_MESS);
        this.title = title;
    }

    public abstract void assignMember(Member member);

    private void setDescription(String description) {
        validateIntRange(description.length(), DESCRIPTION_MIN_NAME_LEN, DESCRIPTION_MAX_NAME_LEN, DESCRIPTION_ERR_MESS);
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Id: %d%n" +
                "Title: %s%n" +
                "Description: %s%n" +
                "Comments: %n%s" +
                "History: %s", id, title, description, printComments(), printHistory()));
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
