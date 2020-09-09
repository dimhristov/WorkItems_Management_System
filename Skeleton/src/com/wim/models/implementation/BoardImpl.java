package com.wim.models.implementation;

import com.wim.ValidationHelper;
import com.wim.models.contracts.*;

import java.util.ArrayList;
import java.util.List;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.ValidationHelper.validateIsPresent;
import static com.wim.models.common.constants.Constants.*;

public class BoardImpl implements Board {
    private static final String BOARD_NAME_NULL_ERR_MESS = "Board name can't be null";
    private static final String ITEM_NULL_ERR_MESS = "Item can't be null";
    private static final String ITEM_EXIST_ERR_MESS = "Item with name %s has been already added to team.";
    private static final String BUG_DONT_EXIST = "Bug with title %s doesn't exist.";
    private static final String STORY_DONT_EXIST = "Story with title %s doesn't exist.";
    private static final String ITEM_DONT_EXIST = "Item with name %s doesn't exist!";

    private String name;
    private List<WorkItem> workItems;
    private List<String> activityHistory;

    public BoardImpl(String name) {
        setName(name);
        this.workItems = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
    }

    private void setName(String name) {
        ValidationHelper.validateArgumentIsNotNull(name, BOARD_NAME_NULL_ERR_MESS);
        ValidationHelper.validateIntRange(name.length(), BOARD_MIN_NAME_LEN, BOARD_MAX_NAME_LEN, BOARD_ERR_MESS);
        this.name = name;
    }

    @Override
    public Feedback findFeedback(String title) {
        for (WorkItem item : workItems) {
            if (item instanceof Feedback) {
                if (item.getTitle().equals(title)) {
                    return (Feedback) item;
                }
            }
        }
        throw new IllegalArgumentException(String.format(BUG_DONT_EXIST, title));
    }

    public Bug findBug(String title) {
        for (WorkItem item : workItems) {
            if (item instanceof Bug) {
                if (item.getTitle().equals(title)) {
                    return (Bug) item;
                }
            }
        }
        throw new IllegalArgumentException(String.format(BUG_DONT_EXIST, title));
    }

    @Override
    public Story findStory(String title) {
        for (WorkItem item : workItems) {
            if (item instanceof Story) {
                if (item.getTitle().equals(title)) {
                    return (Story) item;
                }
            }
        }
        throw new IllegalArgumentException(String.format(STORY_DONT_EXIST, title));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<WorkItem> getWorkItems() {
        return this.workItems;
    }

    @Override
    public WorkItem getItem(String name) {
        for (WorkItem item : workItems) {
            if (item.getTitle().equals(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format(ITEM_DONT_EXIST, name));
    }

    @Override
    public List<String> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public void addItemToBoard(WorkItem item) {
        validateArgumentIsNotNull(item, ITEM_NULL_ERR_MESS);
        validateIsPresent(workItems, item, String.format(ITEM_EXIST_ERR_MESS, item.getTitle()));
        this.workItems.add(item);
    }

    @Override
    public void addToHistory(String activity) {
        validateArgumentIsNotNull(activity, THERE_IS_NOT_ANY_ACTIVITY);
        activityHistory.add(activity);
    }

    @Override
    public void removeItemFromBoard(WorkItem item) {
        validateArgumentIsNotNull(item, ITEM_NULL_ERR_MESS);
        this.workItems.remove(item);
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
        sb.append(String.format("▆▆▆ Board: %s", this.getName()));
        sb.append(System.lineSeparator());
        sb.append(listWorkItems());
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}


