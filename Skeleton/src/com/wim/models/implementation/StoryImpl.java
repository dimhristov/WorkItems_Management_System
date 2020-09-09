package com.wim.models.implementation;

import com.wim.models.common.enums.*;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Story;
import com.wim.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.models.common.constants.Constants.ASSIGNEE_ERR_MESS;

public class StoryImpl extends WorkItemImpl implements Story {


    private static final String ASSIGNEE_DOESNT_EXIST = "Asignee with name %s doesn't exist";
    private StorySizeType sizeType;
    private StoryStatusType statusType;
    private List<Member> assignee;
    private PriorityType priority;

    public StoryImpl(String title, String description, String sizeType, String priority) {
        super(title, description);
        assignee = new ArrayList<>();
        this.sizeType = StorySizeType.valueOf(sizeType.toUpperCase());
        this.statusType = StoryStatusType.NOTDONE;
        this.priority = PriorityType.valueOf(priority.toUpperCase());
    }

    @Override
    public void assignMember(Member member) {
        this.assignee.add(member);
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
    public List<Member> getListAssignee() {
        return this.assignee;
    }

    @Override
    public void removeAsignee(Member member) {
        this.assignee.remove(member);
    }

    @Override
    public void unassignMember(Member member) {
        this.assignee.remove(member);
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    public StorySizeType getSize() {
        return this.sizeType;
    }

    @Override
    public  String getItemsStatus (){
        return this.statusType.toString();
    }

    @Override
    public PriorityType getPriorityType () {
        return this.priority;
    }

    @Override
    public StoryStatusType getStatusType() {
        return statusType;
    }

    @Override
    public void changePriority(String priority) {
        this.priority = PriorityType.valueOf(priority.toUpperCase());
    }

    @Override
    public void changeStatusStory (String statusType){
        this.statusType= StoryStatusType.valueOf(statusType.toUpperCase());
    }

    @Override
    public void changeSizeStory (String sizeType){
        this.sizeType=StorySizeType.valueOf(sizeType.toUpperCase());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------Story data ----------");
        sb.append(System.lineSeparator());
        sb.append(super.toString());
        sb.append(String.format("Status: %s%n" +
                                "Size: %s%n"+
                                "Priority: %s%n", getStatusType(), getSize(),getPriorityType().toString()));
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
