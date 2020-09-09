package com.wim.models.common.enums;

public enum FeedbackStatusType {
        NEW,UNSCHEDULED,SCHEDULED,DONE;


    @Override
    public String toString() {
        switch (this) {
            case NEW:
                return "New";
            case UNSCHEDULED:
                return "Unscheduled";
            case SCHEDULED:
                return "Scheduled";
            case DONE:
                return "Done";
            default:
                throw new IllegalArgumentException();
        }
    }

}
