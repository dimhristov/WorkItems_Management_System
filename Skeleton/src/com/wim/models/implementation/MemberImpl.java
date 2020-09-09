package com.wim.models.implementation;

import com.wim.ValidationHelper;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;

import static com.wim.ValidationHelper.*;
import static com.wim.models.common.constants.Constants.*;

public class MemberImpl implements Member {

    private String name;
    private List<WorkItem> workItems;
    private List<String> activityHistory;

    public MemberImpl(String name) {
        setName(name);
        this.workItems = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
    }

    private void setName(String name) {
        validateArgumentIsNotNull(name, MEMBER_NAME_NULL_ERR_MESS);
        validateIntRange(name.length(), MEMBER_MIN_NAME_LEN, MEMBER_MAX_NAME_LEN, MEMBER_ERR_MESS);
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<WorkItem> getWorkItems() {
        isListEmpty(this.workItems, EMPTY_LIST_ERR_MESS, workItems.getClass().getSimpleName());
        return this.workItems;
    }

    @Override
    public List<String> getActivityHistory() {
        isListEmpty(this.activityHistory, EMPTY_LIST_ERR_MESS, activityHistory.getClass().getName());
        return this.activityHistory;
    }

    @Override
    public void addToHistory(String activity) {
        validateArgumentIsNotNull(activity, THERE_IS_NOT_ANY_ACTIVITY);
        activityHistory.add(activity);
    }

    @Override
    public void addWorkItem(WorkItem item) {
        this.workItems.add(item);
    }


    @Override
    public String showActivity() {
        StringBuilder sb = new StringBuilder();
        sb.append("✎ Activity History");
        sb.append(System.lineSeparator());
        if (activityHistory.size() == 0) {
            sb.append("There isn't any history to show");
            sb.append(System.lineSeparator());
        } else {
            for (String activity : activityHistory) {
                sb.append(" ⤤ ");
                sb.append(activity);
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String listWorkItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("✎ List of Work Items");
        sb.append(System.lineSeparator());
        if (workItems.size() == 0) {
            sb.append("There aren't any work items");
            sb.append(System.lineSeparator());
        } else {
            for (WorkItem workItem : workItems) {
                sb.append(workItem.toString());
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("◀◀◀ Member: %s         ", this.getName()));
        sb.append(System.lineSeparator());
        sb.append("▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔");
        sb.append(System.lineSeparator());
        sb.append(listWorkItems());
        sb.append(System.lineSeparator());
        sb.append(showActivity());
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
