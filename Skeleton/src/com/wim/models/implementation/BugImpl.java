package com.wim.models.implementation;

import com.wim.models.common.enums.BugStatusType;
import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.SeverityType;
import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Member;
import java.util.ArrayList;
import java.util.List;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.models.common.constants.Constants.ASSIGNEE_ERR_MESS;

public class BugImpl extends WorkItemImpl implements Bug {

    private static final String ASSIGNEE_DOESNT_EXIST = "Assignee with name %s doesn't exist";
    private List<String> stepsToReproduce;
    private BugStatusType bugType;
    private PriorityType priority;
    private SeverityType severity;
    private List<Member> assignee;

    public BugImpl(String title, String description, String priority, String severity, List<String> stepsToReproduce) {
        super(title, description);
        assignee = new ArrayList<>();
        this.bugType = BugStatusType.ACTIVE;
        this.stepsToReproduce = new ArrayList<>(stepsToReproduce);
        this.priority = PriorityType.valueOf(priority.toUpperCase());
        this.severity = SeverityType.valueOf(severity.toUpperCase());
    }

    @Override
    public void changePriority(String priority) {
        this.priority = PriorityType.valueOf(priority.toUpperCase());
    }

    @Override
    public String getAsignee(String name) {
        for (Member member : assignee) {
            if (member.getName().equals(name)) {
                return  member.getName();
            }
        }
        return String.format(ASSIGNEE_DOESNT_EXIST, name);
    }
    @Override
    public List<Member> getListAssignee () {
        return assignee;
    }

    @Override
    public void changeBugSeverity(String severity){this.severity=SeverityType.valueOf(severity.toUpperCase());}

    @Override
    public void changeBugStatus(String bugType){this.bugType=BugStatusType.valueOf(bugType.toUpperCase());}

    @Override
    public void assignMember(Member member) {
        this.assignee.add(member);
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public  String getItemsStatus (){
        return this.bugType.toString();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    public List<String> getStepsToReproduce() {
        return stepsToReproduce;
    }

    @Override
    public SeverityType getSeverity() {
        return this.severity;
    }

    @Override
    public BugStatusType getBugType() {
        return bugType;
    }

    @Override
    public PriorityType getPriorityType () {
        return this.priority;
    }

    @Override
    public void removeAsignee(Member member) {
        this.assignee.remove(member);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------Bug data ----------");
        sb.append(System.lineSeparator());
        sb.append(super.toString());
        sb.append(String.format("Status: %s%n" +
                                "Step to reproduce: %s%n" +
                                "Priority: %s%n" +
                                "Severity: %s%n", getBugType(), getStepsToReproduce(),
                                                  getPriorityType().toString(), getSeverity()));
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
