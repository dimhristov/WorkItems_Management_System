package com.wim.models.common.enums;

// to check for the default: throw new Exception
public enum WorkItemType {
    BUG,
    STORY,
    FEEDBACK;

    @Override
    public String toString() {
        switch (this) {
            case BUG:
                return "Bug";
            case STORY:
                return "Story";
            case FEEDBACK:
                return "Feedback";
            default:
                throw new IllegalArgumentException();
        }
    }

}
