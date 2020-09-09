package com.wim.models.contracts;

import java.util.List;

public interface Board {

    Feedback findFeedback(String title);

    Bug findBug(String title);

    Story findStory(String title);

    String getName();

    List<WorkItem> getWorkItems();

    WorkItem getItem(String name);

    List<String> getActivityHistory();

    void addItemToBoard(WorkItem item);

    void addToHistory(String activity);

    void removeItemFromBoard(WorkItem item);

    String listWorkItems();
}
