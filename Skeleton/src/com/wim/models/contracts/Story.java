package com.wim.models.contracts;

import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.StorySizeType;
import com.wim.models.common.enums.StoryStatusType;

import java.util.List;

public interface Story extends WorkItem {

    StorySizeType getSize ();

    StoryStatusType getStatusType ();

    PriorityType getPriorityType ();

    void changePriority(String priority);

    void assignMember(Member member);

    List<Member> getListAssignee();

    void unassignMember(Member member);

    void changeStatusStory (String statusType);

    void changeSizeStory (String sizeType);
}
