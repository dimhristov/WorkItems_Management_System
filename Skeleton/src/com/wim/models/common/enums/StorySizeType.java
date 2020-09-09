package com.wim.models.common.enums;

public enum StorySizeType {
    LARGE(3),
    MEDIUM(2),
    SMALL(1);


    private int index;

    public int getIndex() {
        return this.index;
    }

    StorySizeType(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        switch (this) {
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
