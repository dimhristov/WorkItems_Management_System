package com.wim.models.common.enums;

public enum SeverityType {
    CRITICAL(3),
    MAJOR(2),
    MINOR(1);

    private int index;

    public int getIndex () {
        return this.index;
    }

    SeverityType (int index) {
        this.index = index;
    }


    @Override
    public String toString () {
        switch (this) {
            case CRITICAL:
                return "Critical";
            case MAJOR:
                return "Major";
            case MINOR:
                return "Minor";
            default:
                throw new IllegalArgumentException();
        }
    }
}
