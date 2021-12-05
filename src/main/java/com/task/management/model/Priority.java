package com.task.management.model;

public enum Priority {
    HIGH(1),MEDIUM(2),LOW(3);

    private int value;

    Priority(int value){
        this.value = value;
    }

}
