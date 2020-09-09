package com.wim.models.common.enums;

public enum BugStatusType {
    ACTIVE,
    FIXED;

    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "Active";
            case FIXED:
                return "Fixed";
                  default:
                throw new IllegalArgumentException();
        }
    }
}
