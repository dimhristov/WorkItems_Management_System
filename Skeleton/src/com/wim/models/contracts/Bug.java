package com.wim.models.contracts;

import com.wim.models.common.enums.BugStatusType;
import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.SeverityType;

import java.util.List;

public interface Bug extends WorkItem {


    void changeBugSeverity (String severity);

    void changeBugStatus (String bugType);

    List<String> getStepsToReproduce ();

    SeverityType getSeverity ();

    BugStatusType getBugType ();

    void changePriority(String priority);

    void assignMember(Member member);

    List<Member> getListAssignee ();

    void removeAsignee(Member member);
}
