package com.wim.models.common.enums;

public enum StoryStatusType {
    NOTDONE,INPROGRESS,DONE;

    @Override
    public String toString() {
        switch (this) {
            case NOTDONE:
                return "NotDone";
            case INPROGRESS:
                return "InProgress";
            case DONE:
                return "Done";
            default:
                throw new IllegalArgumentException();
        }
    }
}
