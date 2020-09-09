package com.wim.models.common.enums;

public enum WorkItemsStatusTypes {
    ACTIVE, FIXED, NEW, UNSCHEDULED, SCHEDULED, NOTDONE,INPROGRESS,DONE, LARGE, MEDIUM, SMALL;

    @Override
    public String toString () {
        switch (this) {
            case ACTIVE:
                return "Active";
            case FIXED:
                return "Fixed";
            case NEW:
                return "New";
            case UNSCHEDULED:
                return "Unscheduled";
            case SCHEDULED:
                return "Scheduled";
            case DONE:
                return "Done";
            case NOTDONE:
                return "NotDone";
            case INPROGRESS:
                return "InProgress";
            case LARGE:
                return "Large";
            case MEDIUM:
                return "Medium";
            case SMALL:
                return "Small";
            default:
                throw new IllegalArgumentException();
        }
    }
}