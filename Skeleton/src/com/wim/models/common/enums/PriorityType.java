package com.wim.models.common.enums;

public enum PriorityType {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private int index;

    public int getIndex() {
        return this.index;
    }

    PriorityType(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        switch (this) {
            case HIGH:
                return "High";
            case MEDIUM:
                return "Medium";
            case LOW:
                return "Low";
            default:
                throw new IllegalArgumentException();
        }
    }
}
