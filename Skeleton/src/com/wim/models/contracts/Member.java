package com.wim.models.contracts;

import java.util.List;

public interface Member {

     String getName();

     List<WorkItem> getWorkItems();

     List<String> getActivityHistory();

     void addToHistory (String activity);

     void addWorkItem(WorkItem item);

     String showActivity();

     String listWorkItems();




}
