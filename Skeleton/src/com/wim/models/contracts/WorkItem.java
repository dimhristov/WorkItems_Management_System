package com.wim.models.contracts;

import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.WorkItemsStatusTypes;

import java.util.List;

public interface WorkItem {

    PriorityType getPriorityType ();

    String getTitle ();

    String getAsignee(String name);

    List<Comment> getComments();

    String printComments();

    void assignMember(Member member);

    String printHistory();

    String getDescription ();

    List<String> getHistory ();

    void addComment (Comment comment);

    String getItemsStatus ();

    void addToHistory(String message);

    void removeAsignee(Member member);
}
